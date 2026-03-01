package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "User details")
public record UserResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "User email address", example = "user@example.com")
        String email,

        @Schema(description = "User display name", example = "John Doe")
        String name,

        @Schema(description = "Timestamp when the user was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
