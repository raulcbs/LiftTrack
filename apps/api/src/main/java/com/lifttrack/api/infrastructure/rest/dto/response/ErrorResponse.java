package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(description = "Error response returned when a request fails")
public record ErrorResponse(
        @Schema(description = "HTTP status code", example = "400")
        int status,

        @Schema(description = "Error message describing what went wrong", example = "Name cannot be null or blank")
        String message,

        @Schema(description = "Timestamp when the error occurred", example = "2026-03-01T10:00:00Z")
        Instant timestamp) {
}
