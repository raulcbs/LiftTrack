package com.lifttrack.api.infrastructure.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request body for refreshing an access token")
public record RefreshTokenRequest(
        @Schema(description = "Refresh token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        @NotBlank(message = "Refresh token cannot be blank")
        String refreshToken) {
}
