package embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

public class SimilitudMain {
    static void main() {
        AllMiniLmL6V2EmbeddingModel model = new AllMiniLmL6V2EmbeddingModel();

//        Embedding e1 = model.embed("Me gustan los perros").content();
//        Embedding e2 = model.embed("Amo a los caninos").content();
//        Embedding e3 = model.embed("El clima está soleado").content();
        Embedding e1 = model.embed("qué hora es?").content();
        Embedding e2 = model.embed("Obtiene la fecha y hora actual").content();
        Embedding e3 = model.embed("Calcula el área de un círculo dado su radio").content();


        // Calcular similitud coseno
        double sim12 = CosineSimilarity.between(e1, e2);
        double sim13 = CosineSimilarity.between(e1, e3);

        System.out.println("perros vs caninos: " + sim12); // ~0.85
        System.out.println("perros vs clima:   " + sim13); // ~0.15


    }
}

