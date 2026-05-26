package com.programacion.taller3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestTokenizacion {
    public static final String PATH = "01.embeddings/the-verdict.txt";

    public static List<Pair> vocabulary(String fileName) throws Exception {

        String raw_text = Files.lines(Paths.get(fileName))
                .reduce(String::concat)
                .orElse("");

        String regex = "(?=[,.:;?_!\\\"()']|--|\\s)|(?<=[,.:;?_!\\\"()']|--|\\s)";

        var tokens = raw_text.split(regex);

        var preprocessed = Stream.of(tokens)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        var allWords = preprocessed.stream()
                .distinct()
                .sorted()
                .toList();

        // El resto del método no es visible en la imagen,
        // pero usualmente retornaría la lista vocab generada a partir de allWords.
        AtomicInteger counter = new AtomicInteger(0);
        var vocab = allWords.stream()
                .map(it -> new Pair(counter.getAndIncrement(), it))
                .toList();
        return vocab;
    }

    public static List<Pair> vocabularyEx(String fileName) throws Exception {

        String raw_text = Files.lines(Paths.get(fileName))
                .reduce(String::concat)
                .orElse("");

        String regex = "(?=[,.:;?_!\\\"()']|--|\\s)|(?<=[,.:;?_!\\\"()']|--|\\s)";

        var tokens = raw_text.split(regex);

        var preprocessed = Stream.of(tokens)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();

        var allWords = preprocessed.stream()
                .distinct()
                .sorted().collect(Collectors.toList());

        //En este se aumentan los tokens
        allWords.add("<|endoftext|>");
        allWords.add("<|unk|>");


        // El resto del método no es visible en la imagen,
        // pero usualmente retornaría la lista vocab generada a partir de allWords.
        AtomicInteger counter = new AtomicInteger(0);
        var vocab = allWords.stream()
                .map(it -> new Pair(counter.getAndIncrement(), it))
                .toList();
        return vocab;
    }

    public static void main(String[] args) throws Exception {
        var vocab = vocabulary(PATH);
        vocab.stream()
                .takeWhile(it -> it.tokenId() < 51)
                .forEach(System.out::println);

        //--
        var text = "\"It's the last he painted, you know,\" Mrs. Gisburn said with pardonable pride:";

        SimpleTokenizerV1 tokenizer = new SimpleTokenizerV1(vocab);

        var ids = tokenizer.encode(text);

        System.out.println(ids);
        System.out.println("DECODER: " + tokenizer.decode(ids));

        var vocabEx = vocabularyEx(PATH);

        var text1 = "Hello, do you like tea?";
        var text2 = "In the sunlit terraces of the palace.";
        text = text1 + " <|endoftext|> " + text2;


        vocabEx.stream()
                .skip(vocabEx.size() - 5)
                .forEach(System.out::println);

        SimpleTokenizerV2 tokenizer2 = new SimpleTokenizerV2(vocabEx);

        var ids2 = tokenizer.encode(text);

        System.out.println(ids2);
        System.out.println("DECODER: " + tokenizer.decode(ids2));

    }

}
