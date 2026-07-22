package com.programacion.taller3.prueba.rest;

import com.programacion.taller3.prueba.services.IngestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequiredArgsConstructor
public class IngestController {

    final IngestService ingestService;

    @PostMapping(path = "/api/ingest", consumes = "multipart/form-data")
    public ResponseEntity<String> ingest(@RequestParam("file") MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("El archivo es obligatorio");
        }

        String original = file.getOriginalFilename() == null ? "upload.pdf" : file.getOriginalFilename();
        if (!original.toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("Solo se aceptan archivos PDF");
        }

        Path tempFile = Files.createTempFile("ingest-", ".pdf");
        try {
            file.transferTo(tempFile.toFile());
            int chunks = ingestService.ingest(tempFile.toFile());
            return ResponseEntity.ok("Ingerido OK: " + chunks + " chunks en la collection 'examen_rag'");
        } finally {
            Files.deleteIfExists(tempFile);
        }
    }
}
