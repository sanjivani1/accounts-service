package com.eazybytes.accounts.service.impl;

import com.eazybytes.accounts.dto.AiRequestDto;
import com.eazybytes.accounts.service.IAiService;
import org.apache.catalina.util.ResourceSet;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * Implementation of AI Service using Spring AI
 */
@Service
public class AiServiceImpl implements IAiService {

    private final ChatClient chatClient;

    @Value("classpath:/PromptTemplate/userPrompts.st")
    Resource userPromptTemplate;

    public AiServiceImpl(@Qualifier("ChatConfig") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chat(String prompt) {
        return chatClient.prompt()
                .advisors(new SimpleLoggerAdvisor())
                .user(prompt)
                .call()
                .content();
    }

    @Override
    public String getBankingAssistance(String query) {
        return chatClient.prompt()
                .user(query)
                .call()
                .content();
    }

    @Override
    public String email(String customerName, AiRequestDto request) {
        return chatClient.prompt()
                .system("""
                        You are a assistant of bank customer customer will reach out to you for sending email related to banking queries you help the customers to send email
                        """)
                .user(promptTemplate -> promptTemplate
                        .text(userPromptTemplate)
                        .param("customerName", customerName)
                        .param("customerMessage", request.getPrompt()))
                .call().content();
    }
}

