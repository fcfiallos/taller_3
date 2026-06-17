plugins {
    java
    id("org.springframework.boot") version "4.0.0"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "org.example"
version = "unspecified"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

extra["springAiVersion"] = "2.0.0"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.ai:spring-ai-starter-model-openai") //infraestructura que tiene chatcito, cambia para gemini en la dependencia
    implementation("org.springframework.boot:spring-boot-starter-actuator")

    //leer formatos -- reader
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    implementation("org.springframework.ai:spring-ai-tika-document-reader")

    //embeddings
  //  implementation("org.springframework.ai:spring-ai-starter-model-transformers")

    // vector store
    implementation("org.springframework.ai:spring-ai-starter-vector-store-qdrant")

    // camel -- EIPS
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:4.20.0")
    implementation("org.apache.camel.springboot:camel-file-starter:4.20.0")

    //--Docker compose
    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")

   // implementation("com.microsoft.onnxruntime:onnxruntime_gpu:1.26.0")


}


dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}