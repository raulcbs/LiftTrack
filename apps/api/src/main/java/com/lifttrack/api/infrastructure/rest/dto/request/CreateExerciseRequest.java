package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Schema(description = "Request body for creating a new exercise")
public record CreateExerciseRequest(
        @Schema(description = "Name of the exercise", example = "Bench Press")
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Schema(description = "UUID of the muscle group this exercise targets", example = "550e8400-e29b-41d4-a716-446655440000")
        @NotNull(message = "Muscle group UUID is required")
        UUID muscleGroupUuid,

        @Schema(description = "UUID of the user creating the exercise (null for global exercises)", example = "550e8400-e29b-41d4-a716-446655440001")
        UUID userUuid) {
}
