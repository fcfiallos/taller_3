package com.uce.examenrag.controller;

import com.uce.examenrag.dto.ChatRequest;
import com.uce.examenrag.dto.ChatResponse;
import com.uce.examenrag.service.RagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final RagService ragService;

    @PostMapping(
            value = "/api/chat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ChatResponse chat(@RequestBody ChatRequest request) {
        return ragService.answer(request.message(), request.conversationId());
    }
}
