package com.uce.examenrag.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class PdfTools {

    // Ajusta esta ruta a la carpeta real donde tengas tus PDFs de origen
    private static final String CARPETA_DOCUMENTOS = "C:/uce/documentos";

    @Tool(description = "Lista los archivos PDF disponibles en la carpeta de documentos del sistema")
    public String listarPdfsDisponibles() {
        File carpeta = new File(CARPETA_DOCUMENTOS);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            return "La carpeta de documentos no existe: " + CARPETA_DOCUMENTOS;
        }

        File[] pdfs = carpeta.listFiles((dir, name) -> name.toLowerCase().endsWith(".pdf"));

        if (pdfs == null || pdfs.length == 0) {
            return "No hay archivos PDF en la carpeta de documentos.";
        }

        return Arrays.stream(pdfs)
                .map(File::getName)
                .collect(Collectors.joining(", "));
    }

    @Tool(description = "Consulta metadata (tamano en KB y fecha de modificacion) de un PDF por su nombre de archivo")
    public String consultarMetadataDocumento(String nombreArchivo) {
        File archivo = new File(CARPETA_DOCUMENTOS, nombreArchivo);

        if (!archivo.exists()) {
            return "No se encontro el archivo: " + nombreArchivo;
        }

        long tamanoKb = archivo.length() / 1024;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String fechaModificacion = sdf.format(new Date(archivo.lastModified()));

        return "Archivo: %s | Tamano: %d KB | Ultima modificacion: %s"
                .formatted(nombreArchivo, tamanoKb, fechaModificacion);
    }
}
