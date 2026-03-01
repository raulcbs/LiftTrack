package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for creating a new routine day")
public record CreateRoutineDayRequest(
        @Schema(description = "Day of the week (1 = Monday, 7 = Sunday)", example = "1", minimum = "1", maximum = "7")
        @Min(value = 1, message = "Day of week must be between 1 and 7")
        @Max(value = 7, message = "Day of week must be between 1 and 7")
        int dayOfWeek,

        @Schema(description = "Name of the routine day", example = "Push Day")
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Schema(description = "Sort order within the routine", example = "0", minimum = "0")
        @Min(value = 0, message = "Sort order must be zero or positive")
        int sortOrder) {
}
