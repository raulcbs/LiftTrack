package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for user login")
public record LoginRequest(
        @Schema(description = "User email address", example = "user@example.com")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,

        @Schema(description = "User password", example = "securePassword123")
        @NotBlank(message = "Password cannot be blank")
        String password) {
}
