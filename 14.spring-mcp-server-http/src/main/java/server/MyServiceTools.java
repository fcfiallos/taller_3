package server;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

@Service
public class MyServiceTools {
    @McpTool(description = "Add two numeric values")
    public String sumar(
            @McpToolParam(description = "First numeric value") float x,
            @McpToolParam(description = "Second numeric value") float y) {
        return String.valueOf(x + y);
    }
}

