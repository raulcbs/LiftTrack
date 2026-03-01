package com.lifttrack.api.infrastructure.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Authentication response with tokens and user details")
public record AuthResponse(
        @Schema(description = "JWT access token for API authentication")
        String accessToken,

        @Schema(description = "JWT refresh token for obtaining new access tokens")
        String refreshToken,

        @Schema(description = "Authenticated user details")
        UserResponse user) {
}
