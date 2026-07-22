package com.programacion.taller3.prueba.services;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngestService {

    final VectorStore vectorStore;

    public int ingest(File file) {
        var resource = new FileSystemResource(file);
        var reader = new PagePdfDocumentReader(resource);
        List<Document> documents = reader.get();

        var splitter = TokenTextSplitter.builder()
                .withChunkSize(300)
                .build();
        List<Document> chunks = splitter.split(documents);

        vectorStore.add(chunks);

        return chunks.size();
    }
}
