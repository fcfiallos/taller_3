package com.programacion.taller3.utils;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.PartialResponse;
import dev.langchain4j.model.chat.response.PartialResponseContext;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;

import java.util.concurrent.atomic.AtomicInteger;

public class MyStreamingChatResponseHandler implements StreamingChatResponseHandler {
    AtomicInteger count = null;
    public MyStreamingChatResponseHandler() {
        this.count = count;
    }
    @Override
    public void onPartialResponse(String partialResponse) {

        System.out.print(partialResponse);
        System.out.flush();
    }

    @Override
    public void onPartialResponse(PartialResponse partialResponse, PartialResponseContext context) {
        StreamingChatResponseHandler.super.onPartialResponse(partialResponse, context);
    }


    @Override
    public void onCompleteResponse(ChatResponse completeResponse) {
        System.out.println();
        System.out.println("[Generación completa]");


        count.decrementAndGet();
    }

    @Override
    public void onError(Throwable error) {

        error.printStackTrace();
    }
}
