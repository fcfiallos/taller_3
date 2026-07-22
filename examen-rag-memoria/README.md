# RAG + Memoria - Universidad Central del Ecuador

Proyecto Gradle autocontenido: Spring Boot + Spring AI 2.0, RAG manual
(retrieval con vectorStore.similaritySearch, sin advisor automatico para
el contexto documental) combinado con memoria conversacional usando
MessageWindowChatMemory + MessageChatMemoryAdvisor y conversationId
explicito para soportar multiples sesiones simultaneas.

## PASOS PARA EJECUTAR

### Paso 1: Modelo de embeddings
Descarga el modelo ONNX de `all-MiniLM-L6-v2` y colocalo en:
```
src/main/resources/models/model.onnx
src/main/resources/models/tokenizer.json
```

### Paso 2: Variables de entorno del LLM
```bash
export OPENAI_API_KEY=tu_api_key_de_groq_o_openai
export OPENAI_BASE_URL=https://api.groq.com/openai   # o el que uses
export OPENAI_MODEL=llama-3.1-8b-instant
```

### Paso 3: Levantar la aplicacion
```bash
./gradlew bootRun
```
Levanta Qdrant automaticamente via Docker Compose (spring-boot-docker-compose).

## ENDPOINTS

### Subir e indexar un PDF
```bash
curl -X POST http://localhost:8080/api/ingest \
  -F "file=@documento.pdf"
```

### Preguntar (con memoria por conversationId)
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Cuantas modalidades de titulacion existen?", "conversationId": "estudiante1"}'
```

### Pregunta de seguimiento (misma conversacion, usa memoria)
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Y quien las aprueba?", "conversationId": "estudiante1"}'
```
El modelo entiende que "las" se refiere a las modalidades mencionadas en
el turno anterior, gracias al MessageChatMemoryAdvisor.

### Otra sesion, memoria completamente aislada
```bash
curl -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '{"message": "Hola, tengo una pregunta", "conversationId": "estudiante2"}'
```

Si no envias `conversationId`, se usa `"default"` automaticamente
(ver ChatRequest.java).

## Diferencias clave vs RAG puro
- ChatMemoryConfig.java: nuevo bean ChatMemory (MessageWindowChatMemory, 20 mensajes).
- ChatClientConfig.java: agrega MessageChatMemoryAdvisor como defaultAdvisor.
- ChatRequest / ChatResponse: agregan el campo conversationId.
- RagService: pasa el conversationId al advisor via
  `.advisors(spec -> spec.param(ChatMemory.CONVERSATION_ID, conversationId))`.
