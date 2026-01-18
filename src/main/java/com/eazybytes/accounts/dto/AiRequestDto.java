package com.eazybytes.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "AiRequest", description = "Schema to hold AI chat request")
public class AiRequestDto {

    @NotEmpty(message = "Prompt cannot be empty")
    @Schema(description = "The prompt/question to send to AI", example = "What types of accounts does EazyBank offer?")
    private String prompt;
}

