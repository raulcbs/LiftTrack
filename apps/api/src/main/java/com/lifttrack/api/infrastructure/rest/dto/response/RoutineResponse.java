package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Routine details")
public record RoutineResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the user who owns this routine", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID userUuid,

        @Schema(description = "Name of the routine", example = "Push Pull Legs")
        String name,

        @Schema(description = "Description of the routine", example = "6-day split focusing on compound movements")
        String description,

        @Schema(description = "Whether the routine is currently active", example = "true")
        boolean active,

        @Schema(description = "Timestamp when the routine was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt,

        @Schema(description = "Timestamp when the routine was last updated", example = "2026-03-01T12:00:00Z")
        Instant updatedAt) {
}
