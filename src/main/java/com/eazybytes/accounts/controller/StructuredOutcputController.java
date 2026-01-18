package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.CountryCities;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ai")
public class StructuredOutcputController {

    public ChatClient chatClient;

    public StructuredOutcputController(ChatClient.Builder chatClientBuilder){
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/countries")
    public ResponseEntity<List<String>> getCountries(@RequestParam String message){
        List<String> countries = chatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<List<String>>() {});

        return ResponseEntity.ok(countries);
    }

}
