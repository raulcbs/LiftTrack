package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Request body for adding an exercise to a workout session")
public record CreateWorkoutSessionExerciseRequest(
        @Schema(description = "UUID of the exercise performed", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "Exercise UUID is required")
        UUID exerciseUuid,

        @Schema(description = "Sort order within the session", example = "0", minimum = "0")
        @Min(value = 0, message = "Sort order must be zero or positive")
        int sortOrder,

        @Schema(description = "Optional notes for this exercise", example = "Used belt for last 2 sets")
        String notes) {
}
