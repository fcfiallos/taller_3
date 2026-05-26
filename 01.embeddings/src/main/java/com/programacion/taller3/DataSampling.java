package com.programacion.taller3;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.IntArrayList;
import com.knuddels.jtokkit.api.ModelType;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.IntStream;

public class DataSampling {
    public static final String PATH = "01.embeddings/the-verdict.txt";

    static void main() throws Exception {
        String raw_text = Files.lines(Paths.get(PATH))
                .reduce(String::concat)
                .orElse("");

        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding tokenizer = registry.getEncodingForModel(ModelType.TEXT_DAVINCI_003);

        var enc_text = tokenizer.encode(raw_text);
        var enc_text_boxed = enc_text.boxed();
        var enc_sample = enc_text_boxed.subList(50, enc_text_boxed.size());

        System.out.println("Tokens count: " + enc_text.size());

        //-
        int contextSize = 4;

        var x = enc_sample.subList(0, 4);
        var y = enc_sample.subList(1, contextSize + 1);

        System.out.println(x);
        System.out.println("    " + y);

        IntArrayList inputTokens = new IntArrayList();
        x.forEach(inputTokens::add);

        IntArrayList targetTokens = new IntArrayList();
        y.forEach(targetTokens::add);

        System.out.println(tokenizer.decode(inputTokens));
        System.out.println("    " + tokenizer.decode(targetTokens));

        //-- Generacion de los (9input y target)


        //-- Generacion de los (input y target)
        // 1. Declaras la lista fuera del bucle
        List<DatasetItem> dataset = new ArrayList<>();

        List<Integer> tokensIds = tokenizer.encode(raw_text).boxed();
        int maxLength = 4;

        IntStream.range(0, tokensIds.size() - maxLength)
                .forEach(i -> {
                    var inputChunk = tokensIds.subList(i, i + maxLength);
                    // Si tu record espera una lista para el target, se pasa el subList
                    var targetChunk = tokensIds.subList(i + 1, i + maxLength + 1);

                    dataset.add(new DatasetItem(inputChunk, targetChunk));
                });


        System.out.println("Data size: " + dataset.size());
        System.out.println(dataset.get(0));
        System.out.println(dataset.get(1));



    }
}

