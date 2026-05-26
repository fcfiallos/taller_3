package embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.output.Response;

import java.nio.file.Paths;

public class EmbeddingModelMain {
    static void main() {
        var pathToModel = Paths.get("c:/tools/onnx/model.onnx");
        var pathToTokenizer = Paths.get("c:/tools/onnx/tokenizer.json");

        // OnnxEmbeddingModel model = new OnnxEmbeddingModel(pathToModel, pathToTokenizer, PoolingMode.MEAN);
// con eso no es necesario descargar el modelo
        AllMiniLmL6V2EmbeddingModel model = new AllMiniLmL6V2EmbeddingModel();


        Response<Embedding> response = model.embed("hola, cómo estas");
        Embedding embedding = response.content();

        System.out.println("Dimension: " + embedding.vector().length);
        System.out.println(embedding);
        System.out.println(embedding.vectorAsList());


    }
}

