package com.programacion.taller3.prueba.rest;

public record ChatRequest(String message) {
    public ChatRequest {
        if (message == null || message.isBlank()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
    }
}
