# Modelos ONNX para embeddings locales

El embedding Transformers/ONNX requiere **dos archivos** que deben estar en esta carpeta (`src/main/resources/models/`):

- `model.onnx`
- `tokenizer.json`

## Opciones de modelos compatibles

Cualquier modelo de sentence-similarity exportado a ONNX sirve. Recomendados:

| Modelo | Dimensión | Tamaño | URL HuggingFace |
| ------ | --------- | ------ | --------------- |
| `sentence-transformers/all-MiniLM-L6-v2` (ONNX) | 384 | ~90 MB | https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/tree/main/onnx |
| `Xenova/all-MiniLM-L6-v2`                  | 384 | ~90 MB | https://huggingface.co/Xenova/all-MiniLM-L6-v2 |
| `intfloat/e5-small-v2` (ONNX)              | 384 | ~130 MB| https://huggingface.co/intfloat/e5-small-v2/tree/main/onnx |
| `BAAI/bge-small-en-v1.5` (ONNX)            | 384 | ~130 MB| https://huggingface.co/BAAI/bge-small-en-v1.5/tree/main/onnx |
| `intfloat/multilingual-e5-base` (ONNX)     | 768 | ~1.0 GB| https://huggingface.co/intfloat/multilingual-e5-base/tree/main/onnx |

## Pasos (ejemplo con `all-MiniLM-L6-v2`)

1. Bajar el archivo ONNX del modelo:

   ```bash
   curl -L -o model.onnx \
     https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/resolve/main/onnx/model.onnx
   ```

2. Bajar el tokenizer:

   ```bash
   curl -L -o tokenizer.json \
     https://huggingface.co/sentence-transformers/all-MiniLM-L6-v2/resolve/main/tokenizer.json
   ```

3. Verificar que ambos archivos estén en `src/main/resources/models/`.

4. Si usas un modelo con dimensión distinta a la del vector store configurado, ajusta la colección en `application.yml` y deja que Qdrant la cree con la dimensión del primer embedding.

## Nota

Los archivos `model.onnx` y `tokenizer.json` **no se versionan** en git (están en `.gitignore` del proyecto raíz).
