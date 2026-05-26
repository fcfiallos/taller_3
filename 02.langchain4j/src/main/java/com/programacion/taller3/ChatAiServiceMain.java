package com.programacion.taller3;

import com.programacion.taller3.utils.AsistenteLegal;
import dev.langchain4j.service.AiServices;

public class ChatAiServiceMain {

    static void main() {
        var model = ChatMain.chatModel();

        var asistente = AiServices.create(AsistenteLegal.class, model);

        var respuesta = asistente.consultar("Cuál es el plazo de la preescripcion para para deudas civiles?");

        System.out.println(respuesta);

        //--------------
        System.out.println("--------------------");
        respuesta = asistente.responder("Cuál es el plazo de la preescripcion para para deudas civiles?");

        System.out.println(respuesta);
    }
}
