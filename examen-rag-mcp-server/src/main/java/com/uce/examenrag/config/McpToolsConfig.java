package com.uce.examenrag.config;

import com.uce.examenrag.tools.PdfTools;
import com.uce.examenrag.tools.VectorStoreTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolsConfig {

    @Bean
    public ToolCallbackProvider tools(PdfTools pdfTools, VectorStoreTools vectorStoreTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(pdfTools, vectorStoreTools)
                .build();
    }
}
