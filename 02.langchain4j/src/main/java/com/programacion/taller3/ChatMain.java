package com.programacion.taller3;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class ChatMain {
    public static ChatModel chatModel(){
        return // debe ser generico no usar OpenAiChatModel
                 OpenAiChatModel.builder()
                .apiKey("api-key-real")
                .modelName("llama-2-7b-chat.Q4_0.gguf")
                .baseUrl("http://localhost:8080") //tratar de conectarse con open ai si no se especifica
                .logRequests(true)
                .logResponses(true)
                .build();

    }
    static void main() {
        // debe ser generico no usar OpenAiChatModel
        ChatModel model = chatModel();

        var respuesta = model.chat("Qué es llama.cpp?");

        System.out.println("[respuesta]");
        System.out.println(respuesta);

    }
}
