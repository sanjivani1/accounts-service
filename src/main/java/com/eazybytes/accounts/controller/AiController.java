package com.eazybytes.accounts.controller;

import com.eazybytes.accounts.dto.AiRequestDto;
import com.eazybytes.accounts.dto.AiResponseDto;
import com.eazybytes.accounts.service.IAiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * REST Controller for AI-powered banking assistance
 */
@Tag(
        name = "AI Banking Assistant",
        description = "REST APIs for AI-powered banking assistance using Spring AI"
)
@RestController
@RequestMapping(path = "/api/ai", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class AiController {

    private final IAiService aiService;

    @Operation(
            summary = "Simple Chat",
            description = "Send a prompt and get an AI-generated response"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = @Content(schema = @Schema(implementation = AiResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @PostMapping("/chat")
    public ResponseEntity<AiResponseDto> chat(@Valid @RequestBody AiRequestDto request) {
        String response = aiService.chat(request.getPrompt());
        AiResponseDto responseDto = new AiResponseDto(
                response,
                "ollama-llama3.2",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Banking Assistant",
            description = "Get AI-powered assistance for banking queries. The AI is trained to help with account-related questions."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = @Content(schema = @Schema(implementation = AiResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @PostMapping("/banking-assistant")
    public ResponseEntity<AiResponseDto> bankingAssistant(@Valid @RequestBody AiRequestDto request) {
        String response = aiService.getBankingAssistance(request.getPrompt());
        AiResponseDto responseDto = new AiResponseDto(
                response,
                "ollama-llama3.2",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(responseDto);
    }

    @Operation(
            summary = "Simple Chat (GET)",
            description = "Send a prompt via query parameter and get an AI-generated response"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful response",
                    content = @Content(schema = @Schema(implementation = AiResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error"
            )
    })
    @GetMapping("/chat")
    public ResponseEntity<AiResponseDto> chatGet(@RequestParam String prompt) {
        String response = aiService.chat(prompt);
        AiResponseDto responseDto = new AiResponseDto(
                response,
                "ollama-llama3.2",
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        );
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/email")
    public String sendEmail(@RequestParam String customerName, @RequestBody AiRequestDto request){
        return aiService.email(customerName, request);

    }
}

