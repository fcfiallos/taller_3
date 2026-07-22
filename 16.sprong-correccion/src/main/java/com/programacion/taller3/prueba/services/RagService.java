package com.programacion.taller3.prueba.services;

import com.programacion.taller3.prueba.rest.ChatResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RagService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/prompts/systemPrompt.st")
    Resource systemPrompt;

    public RagService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public ChatResponse answer(String message) {
        // 1) Retrieval manual (topK = 3) contra Qdrant
        var request = SearchRequest.builder()
                .query(message)
                .topK(3)
                .build();
        List<Document> retrieved = vectorStore.similaritySearch(request);

        // 2) Agregar el contexto al system prompt
        String contexto = retrieved.stream()
                .map(Document::getText)
                .reduce("", (a, b) -> a + System.lineSeparator() + b)
                .trim();

        // 3) Generar la respuesta con chatClient.call()
        String answer = chatClient.prompt()
                .system(spec -> spec
                        .text(systemPrompt)
                        .param("contexto", contexto))
                .user(message)
                .call()
                .content();

        // 4) Devolver la respuesta + los 3 chunks como sources
        List<String> sources = retrieved.stream()
                .map(Document::getText)
                .toList();

        return new ChatResponse(answer, sources);
    }
}
