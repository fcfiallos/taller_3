package com.uce.examenrag.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestService {

    private final VectorStore vectorStore;
    private static final int BATCH_SIZE = 10;

    public int ingest(Resource pdfResource) {
        TikaDocumentReader reader = new TikaDocumentReader(pdfResource);
        List<Document> documentos = reader.get();

        TokenTextSplitter splitter = TokenTextSplitter.builder()
                .withChunkSize(300)
                .build();
        List<Document> chunks = splitter.split(documentos);

        // Envio en lotes pequenos: mas seguro que un unico add() con todo el PDF
        for (int i = 0; i < chunks.size(); i += BATCH_SIZE) {
            int fin = Math.min(i + BATCH_SIZE, chunks.size());
            List<Document> lote = chunks.subList(i, fin);
            vectorStore.add(lote);
        }

        return chunks.size();
    }
}
