package com.programacion.taller3;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.IntArrayList;
import com.knuddels.jtokkit.api.ModelType;

public class TokkitTokenizer {
    static void main() {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        //Encoding tokenizer = registry.getEncodingForModel(ModelType.GPT_4);
        Encoding tokenizer = registry.getEncodingForModel(ModelType.TEXT_DAVINCI_003);

        // var text = "Hello, do you like tea? <|endoftext|> In the sunlit terraces of someunknownPlace.";
        var text = "Hello, do you like tea? In the sunlit terraces of someunknownPlace.";
       // var text = "someunknownPlace."; // esta palabra no esta en el diccionario

        IntArrayList ids = tokenizer.encodeOrdinary(text);

        System.out.println("Tokens: " + ids.size());
        System.out.println(ids);

        System.out.println("Decoded:" +tokenizer.decode(ids));


    }
}
