package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Routine day details")
public record RoutineDayResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the parent routine", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID routineUuid,

        @Schema(description = "Day of the week (1 = Monday, 7 = Sunday)", example = "1")
        int dayOfWeek,

        @Schema(description = "Name of the routine day", example = "Push Day")
        String name,

        @Schema(description = "Sort order within the routine", example = "0")
        int sortOrder,

        @Schema(description = "Timestamp when the routine day was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
