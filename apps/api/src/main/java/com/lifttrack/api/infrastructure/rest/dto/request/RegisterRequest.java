package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Request body for user registration")
public record RegisterRequest(
        @Schema(description = "User email address", example = "user@example.com")
        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,

        @Schema(description = "User password (minimum 8 characters)", example = "securePassword123")
        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters")
        String password,

        @Schema(description = "User display name", example = "John Doe")
        @NotBlank(message = "Name cannot be blank")
        @Size(max = 100, message = "Name cannot exceed 100 characters")
        String name) {
}
