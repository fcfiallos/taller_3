package com.uce.examenrag.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VectorStoreTools {

    @Autowired
    private VectorStore vectorStore;

    @Tool(description = "Consulta si hay contenido indexado en la base de conocimiento (coleccion Qdrant examen_rag), " +
            "haciendo una busqueda de prueba y contando resultados")
    public String estadisticasColeccionQdrant() {
        try {
            var searchRequest = SearchRequest.builder()
                    .query("titulacion")
                    .topK(100)
                    .build();

            int cantidad = vectorStore.similaritySearch(searchRequest).size();

            if (cantidad == 0) {
                return "La coleccion 'examen_rag' no tiene documentos indexados actualmente.";
            }

            return "La coleccion 'examen_rag' tiene al menos " + cantidad +
                    " fragmentos indexados relacionados con el termino de prueba.";
        } catch (Exception e) {
            return "No se pudo consultar la coleccion: " + e.getMessage();
        }
    }
}
