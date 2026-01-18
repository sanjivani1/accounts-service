package com.eazybytes.accounts.rag;

import com.eazybytes.accounts.constants.VectorData;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RandomDataLoader {

    private static final Logger log = LoggerFactory.getLogger(RandomDataLoader.class);

    private final VectorStore vectorStore;
    private final List<String> documentIds = new ArrayList<>();

    public RandomDataLoader(VectorStore vectorStore){
        this.vectorStore = vectorStore;
    }

    @PostConstruct
    public void loadDataToVectorStore(){
        log.info("Loading data to vector store on application startup...");
        List<Document> documents = VectorData.sentences.stream().map(Document::new).toList();

        // Store document IDs for cleanup on shutdown
        documents.forEach(doc -> documentIds.add(doc.getId()));

        vectorStore.add(documents);
        log.info("Successfully loaded {} documents to vector store", documents.size());
    }

    @PreDestroy
    public void clearVectorStoreOnShutdown(){
        log.info("Clearing vector store on application shutdown...");
        try {
            if (!documentIds.isEmpty()) {
                vectorStore.delete(documentIds);
                log.info("Successfully deleted {} documents from vector store", documentIds.size());
            }
        } catch (Exception e) {
            log.warn("Could not clear vector store on shutdown: {}", e.getMessage());
        }
    }
}
