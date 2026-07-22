package com.uce.examenrag.controller;

import com.uce.examenrag.service.IngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequiredArgsConstructor
public class IngestController {

    private final IngestService ingestService;

    @PostMapping(value = "/api/ingest", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> ingest(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Archivo vacio");
        }

        try {
            InputStream is = file.getInputStream();
            Resource resource = new InputStreamResource(is) {
                @Override
                public String getFilename() {
                    return file.getOriginalFilename();
                }
            };

            int totalChunks = ingestService.ingest(resource);

            return ResponseEntity.ok(
                    "Archivo '%s' indexado: %d chunks".formatted(file.getOriginalFilename(), totalChunks)
            );
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Error leyendo el archivo: " + e.getMessage());
        }
    }
}
