package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.UUID;

@Schema(description = "Workout session exercise details")
public record WorkoutSessionExerciseResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the parent workout session", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID workoutSessionUuid,

        @Schema(description = "UUID of the exercise performed", example = "550e8400-e29b-41d4-a716-446655440002")
        UUID exerciseUuid,

        @Schema(description = "Sort order within the session", example = "0")
        int sortOrder,

        @Schema(description = "Notes for this exercise", example = "Used belt for last 2 sets")
        String notes,

        @Schema(description = "Timestamp when the record was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
