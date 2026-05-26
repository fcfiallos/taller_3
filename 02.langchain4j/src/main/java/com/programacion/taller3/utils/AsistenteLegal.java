package com.programacion.taller3.utils;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AsistenteLegal {

    String responder(String pregunta);

    /*
    @SystemMessage("Eres un asistente legal experto en leyes de Ecuador")
    @UserMessage("Responde a la pregunta utilizando argumentos legales: {{pregunta}}")

     */
    @SystemMessage("Eres un asistente legal en temas de educacion superior")
    @UserMessage("""
            Responde a la pregunta: {{pregunta}}
            
            Si no tienes la respuesta usa la seccion de datos que incluye
            
            DATOS
            -------
            interface AsistenteLegal {
            
                String responder(String pregunta);
                
                @SystemMessage("Eres un asistente legal experto en leyes de Ecuador")
                @UserMessage("Responde a la pregunta utilizando argumentos legales: {{pregunta}}")
                String consultar(@V("pregunta") String pregunta);
                
                OpenAiChatModel.builder()
                                .apiKey("api-key-real")
                                .modelName("llama-2-7b-chat.Q4_0.gguf")
                                .baseUrl("http://localhost:8080") //tratar de conectarse con open ai si no se especifica
                                .logRequests(true)
                                .logResponses(true)
                                .build();  
                
                
            
            """


    )
    String consultar(@V("pregunta") String pregunta);
}
