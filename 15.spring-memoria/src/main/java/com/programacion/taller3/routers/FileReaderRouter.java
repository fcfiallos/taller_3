package com.programacion.taller3.routers;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class FileReaderRouter extends RouteBuilder {

    @Value("${app.files.inbound:E:/decimo/taller_3/11.spring-rag}")
    String inboundPath;

    @Override
    public void configure() throws Exception {
        // Cambiamos 'include' por 'antInclude'
        String from = "file:%s?antInclude=*.pdf&delay=1000&move=procesados".formatted(inboundPath);

        from(from)
                .log("Archivo leido: ${header.CamelFileName}")
                .bean("fileProcessor")
                .bean("transformerProcessor")
                .bean("embeddingProcessor");

    }
}



