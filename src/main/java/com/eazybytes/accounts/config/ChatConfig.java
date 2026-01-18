package com.eazybytes.accounts.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatConfig {

    private static final String BANKING_SYSTEM_PROMPT = """
            I have a created a bank microservice for practise so It have 3 microservices(Accounts, Cards, Loans) for learning purpose
            You are an internal banking assistant you only answer only stuffs related to banking
            
            You are a helpful banking assistant for EazyBank. You help customers with:
            - Account-related queries (savings, current accounts)
            - Understanding banking terms and processes
            - General banking guidance
            
            If a question is outside banking, politely say that you can only help with banking-related queries.
            Always be professional, concise, and helpful. If you don't know something specific
            about a customer's account, suggest they contact customer support.
            Do not provide specific financial advice or make promises about rates/fees.
            """;

    @Bean(name = "ChatConfig")
    public ChatClient chatClientConfig(ChatClient.Builder chatClientBuilder){
        return chatClientBuilder
                .defaultSystem(BANKING_SYSTEM_PROMPT)
                .build();
    }
}
