plugins {
    java
    id("org.springframework.boot") version "4.1.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("io.freefair.lombok") version "9.5.0"
}

group = "com.programacion"
version = "1.0.0"

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
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    //--readers
    implementation("org.springframework.ai:spring-ai-pdf-document-reader")
    implementation("org.springframework.ai:spring-ai-tika-document-reader")

    //--Embeddings
    implementation("org.springframework.ai:spring-ai-starter-model-transformers")

    //--Vector Store
    implementation("org.springframework.ai:spring-ai-starter-vector-store-qdrant")

    //--Docker
    runtimeOnly("org.springframework.boot:spring-boot-docker-compose")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
    }
}

configurations.all {
    exclude(group = "ai.djl.pytorch")
}
