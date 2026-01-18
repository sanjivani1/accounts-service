package com.eazybytes.accounts.service;

import com.eazybytes.accounts.dto.AiRequestDto;

/**
 * Service interface for AI-related operations
 */
public interface IAiService {

    /**
     * Generate a response using AI for the given prompt
     * @param prompt - The user's prompt/question
     * @return AI generated response
     */
    String chat(String prompt);

    /**
     * Get banking assistance for account-related queries
     * @param query - Customer's banking query
     * @return AI generated banking assistance response
     */
    String getBankingAssistance(String query);

    String email(String customerName, AiRequestDto request);
}

