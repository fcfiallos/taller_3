package com.uce.examenrag.dto;

import java.util.List;

public record ChatResponse(String answer, List<String> sources, String conversationId) {
}
