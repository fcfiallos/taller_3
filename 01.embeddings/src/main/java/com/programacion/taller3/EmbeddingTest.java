package com.programacion.taller3;

import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDManager;
import ai.djl.ndarray.types.Shape;
// BORRADO: import ai.djl.nn.core.Embedding; <--- Este causaba el error

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.IntArrayList;
import com.knuddels.jtokkit.api.ModelType;

// IMPORTANTE: Añade este para que funcione .vector()
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2q.AllMiniLmL6V2QuantizedEmbeddingModel;
import dev.langchain4j.model.output.Response;

import java.util.Arrays; // No olvides este para imprimir el vector


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class EmbeddingTest {

    public static final String PATH = "01.embeddings/the-verdict.txt";

    public static void main(String[] args)throws Exception {
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

       // System.out.println(tokenizer.decode(inputTokens));
        //System.out.println("    " + tokenizer.decode(targetTokens));

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

        //--embedding
        int vocalSixe = 50257;
        int putputDim =4;

        try(NDManager manager = NDManager.newBaseManager()){
            NDArray weights = manager.randomUniform(-1.0f, 1.0f, new Shape(vocalSixe, putputDim));

            dataset.stream().limit(2).forEach(item->{

                //cambiamos a long
                var input= item.input().stream().mapToLong(Integer::longValue).toArray();

                NDArray indices = manager.create(input);
                var embedding = weights.get(indices);

                System.out.println("Input indices" + Arrays.toString(input));
                System.out.println("Embedding output shape: " + embedding);
                System.out.println();

            });
        }

        // con LangChain4j

        //-----------------------------------------
        EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();
        var text = "hello";

        Response<Embedding> response = embeddingModel.embed(text);

        float[] vector = response.content().vector();

        System.out.println("Embedding size: " + vector.length);
        System.out.println(Arrays.toString(vector));


    }
}