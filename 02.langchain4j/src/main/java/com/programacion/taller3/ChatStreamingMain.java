package com.programacion.taller3;

import com.programacion.taller3.utils.MyStreamingChatResponseHandler;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;

import static com.programacion.taller3.ChatMain.chatModel;

public class ChatStreamingMain {
    static void main() {
        var ChatModel = OpenAiStreamingChatModel.builder()
                .apiKey("api-key-real")
                .modelName("llama-2-7b-chat.Q4_0.gguf")
                .baseUrl("http://localhost:8080") //tratar de conectarse con open ai si no se especifica
                .logRequests(true)
                .logResponses(true)
                .build();

        ChatModel.chat("¿Que es llama.cpp?", new MyStreamingChatResponseHandler());

        while (true){

        }
    }
}
