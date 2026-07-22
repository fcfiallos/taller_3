package com.programacion.taller3.prueba.rest;

public record ChatRequest(String message, String conversationId) {
    public ChatRequest {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacio");
        }
        if (conversationId == null || conversationId.isBlank()) {
            conversationId = "default";
        }
    }
}
