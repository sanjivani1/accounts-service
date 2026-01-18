package com.eazybytes.accounts.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rag")
public class RagController {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/promptTemplate/SystemPromptRandomDataTemplate.st")
    Resource promptTemplate;

    @Autowired
    public RagController(@Qualifier("chatMemoryChatClient") ChatClient chatClient, VectorStore vectorStore){
        this.chatClient = chatClient;
        this.vectorStore = vectorStore;
    }

    @GetMapping("/random-chat")
    public ResponseEntity<String> randomChat(@RequestParam("username") String username, @RequestParam("message") String message){

        SearchRequest searchRequest = SearchRequest
                .builder()
                .query(message)
                .similarityThreshold(0.5)   //
                .build();
        List<Document> similarDocuments = vectorStore.similaritySearch(searchRequest);
        assert similarDocuments != null;
        String similarContext = similarDocuments.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        String response = chatClient.prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(promptTemplate))
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(response);
    }

}
