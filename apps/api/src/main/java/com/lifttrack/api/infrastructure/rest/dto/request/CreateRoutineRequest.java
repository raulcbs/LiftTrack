package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for creating a new routine")
public record CreateRoutineRequest(
        @Schema(description = "Name of the routine", example = "Push Pull Legs")
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Schema(description = "Optional description of the routine", example = "6-day split focusing on compound movements")
        String description,

        @Schema(description = "Whether the routine is currently active", example = "true")
        boolean active) {
}
