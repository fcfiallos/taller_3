package com.programacion.taller3.prueba.rest;

import java.util.List;

public record ChatResponse(String answer, List<String> sources) {
    public ChatResponse {
        if (answer == null) {
            answer = "";
        }
        if (sources == null) {
            sources = List.of();
        }
    }
}
