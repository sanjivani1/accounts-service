package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AiResponse", description = "Schema to hold AI chat response")
public class AiResponseDto {

    @Schema(description = "The AI generated response")
    private String response;

    @Schema(description = "The model used for generation")
    private String model;

    @Schema(description = "Timestamp of the response")
    private String timestamp;
}

