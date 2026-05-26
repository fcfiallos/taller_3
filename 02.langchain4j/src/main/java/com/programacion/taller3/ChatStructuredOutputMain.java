package com.programacion.taller3;

import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

// 1. Definición del Record de Java para la salida estructurada
record Persona(String nombre, int edad, String ciudad) {}

// 2. Interfaz del servicio de IA con la plantilla de extracción
interface Extractor {
    @UserMessage("Extrae la información de: {{texto}}")
    Persona extraerPersona(@V("texto") String texto);
}

public class ChatStructuredOutputMain {
    static void main() {
        var model = ChatMain.chatModel();

        // Inicialización del servicio AiServices
        Extractor extractor = AiServices.builder(Extractor.class)
                .chatModel(model) // Ajustado al método estándar de LangChain4j
                .build();

        // Ejecución de la extracción de datos mapeada directamente al Record
        Persona p = extractor.extraerPersona("Soy María, tengo 30 años y vivo en Quito");

        // Imprimir el resultado estructurado
        System.out.println(p);
    }
}
