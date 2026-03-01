package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Workout session details")
public record WorkoutSessionResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the user who performed this session", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID userUuid,

        @Schema(description = "UUID of the routine day (null if ad-hoc session)", example = "550e8400-e29b-41d4-a716-446655440002")
        UUID routineDayUuid,

        @Schema(description = "Date of the workout session", example = "2026-03-01")
        LocalDate sessionDate,

        @Schema(description = "Timestamp when the session started", example = "2026-03-01T10:00:00Z")
        Instant startedAt,

        @Schema(description = "Timestamp when the session finished", example = "2026-03-01T11:30:00Z")
        Instant finishedAt,

        @Schema(description = "Notes about the session", example = "Felt strong today")
        String notes,

        @Schema(description = "Timestamp when the session record was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
