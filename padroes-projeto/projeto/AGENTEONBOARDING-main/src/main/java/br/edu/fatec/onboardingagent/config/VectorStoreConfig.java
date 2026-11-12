package br.edu.fatec.onboardingagent.config;

import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class VectorStoreConfig {

    @Bean
    public VectorStore knowledgeVectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    public Resource knowledgeDocument(
            @org.springframework.beans.factory.annotation.Value("classpath:knowledge/git-github.md") Resource recurso) {
        return recurso;
    }
}
