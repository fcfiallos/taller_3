package com.uce.examenrag.service;

import com.uce.examenrag.dto.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/systemPrompt.st")
    private Resource systemPrompt;

    public ChatResponse answer(String message, String conversationId) {

        // 1. Retrieval manual (igual que en el RAG puro)
        var searchRequest = SearchRequest.builder()
                .query(message)
                .topK(3)
                .build();

        List<Document> retrieved = vectorStore.similaritySearch(searchRequest);

        List<String> sources = retrieved.stream()
                .map(Document::getText)
                .toList();

        String contexto = String.join("\n---\n", sources);

        // 2. La memoria NO se maneja aqui manualmente: el MessageChatMemoryAdvisor
        //    (registrado como defaultAdvisor en ChatClientConfig) la inyecta solo,
        //    usando el conversationId que pasamos como parametro del advisor.
        String answer = chatClient.prompt()
                .system(spec -> spec.text(systemPrompt).param("contexto", contexto))
                .user(message)
                .advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .call()
                .content();

        return new ChatResponse(answer, sources, conversationId);
    }
}
