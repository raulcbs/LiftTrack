package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Schema(description = "Request body for creating a new workout session")
public record CreateWorkoutSessionRequest(
        @Schema(description = "UUID of the routine day (optional)", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID routineDayUuid,

        @Schema(description = "Date of the workout session", example = "2026-03-01")
        @NotNull(message = "Session date is required")
        LocalDate sessionDate,

        @Schema(description = "Timestamp when the session started", example = "2026-03-01T10:00:00Z")
        @NotNull(message = "Start time is required")
        Instant startedAt,

        @Schema(description = "Timestamp when the session finished (optional)", example = "2026-03-01T11:30:00Z")
        Instant finishedAt,

        @Schema(description = "Optional notes about the session", example = "Felt strong today")
        String notes) {
}
