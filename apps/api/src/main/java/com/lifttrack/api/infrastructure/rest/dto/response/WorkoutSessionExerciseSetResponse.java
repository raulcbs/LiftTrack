package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Schema(description = "Workout session exercise set details")
public record WorkoutSessionExerciseSetResponse(
        @Schema(description = "Unique identifier", example = "550e8400-e29b-41d4-a716-446655440000")
        UUID uuid,

        @Schema(description = "UUID of the parent workout session exercise", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID workoutSessionExerciseUuid,

        @Schema(description = "Set number within the exercise", example = "1")
        int setNumber,

        @Schema(description = "Number of reps performed", example = "10")
        int reps,

        @Schema(description = "Weight used in kilograms", example = "80.0")
        BigDecimal weight,

        @Schema(description = "Reps in reserve", example = "2")
        Integer rir,

        @Schema(description = "Whether this is a warmup set", example = "false")
        boolean warmup,

        @Schema(description = "Timestamp when the set was created", example = "2026-03-01T10:00:00Z")
        Instant createdAt) {
}
