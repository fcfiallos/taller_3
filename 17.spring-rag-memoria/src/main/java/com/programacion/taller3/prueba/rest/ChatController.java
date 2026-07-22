package com.programacion.taller3.prueba.rest;

import com.programacion.taller3.prueba.services.RagService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final RagService ragService;

    public ChatController(RagService ragService) {
        this.ragService = ragService;
    }

    @PostMapping(path = "/api/chat", consumes = "application/json", produces = "application/json")
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return ragService.answer(request.message());
    }
}
