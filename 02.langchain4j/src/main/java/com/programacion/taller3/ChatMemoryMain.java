package com.programacion.taller3;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;

import java.util.Scanner;

interface  Conversador {
    String chat(String mensaje);
}
public class ChatMemoryMain {
    static void main() {

        var model = ChatMain.chatModel();

        ChatMemory memory = MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();

        Conversador bot = AiServices.builder(Conversador.class)
                .chatMemory(memory)
                .chatModel(model)
                .build();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Mensaje : ");
            String msg = scanner.nextLine();

            if("exit".equalsIgnoreCase(msg)) {
                break;
            }


            var respuesta = bot.chat(msg);

            System.out.println(respuesta);
        }
    }
}
