package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.user.CreateUserUseCase;
import com.lifttrack.api.domain.models.User;
import com.lifttrack.api.infrastructure.rest.dto.mapper.UserResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.LoginRequest;
import com.lifttrack.api.infrastructure.rest.dto.request.RefreshTokenRequest;
import com.lifttrack.api.infrastructure.rest.dto.request.RegisterRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.AuthResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.security.CustomUserDetails;
import com.lifttrack.api.infrastructure.security.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User registration, login, and token refresh")
public class AuthController {

    private final CreateUserUseCase createUserUseCase;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account and returns authentication tokens")
    @ApiResponse(responseCode = "201", description = "User registered successfully",
            content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request or email already exists",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        String hashedPassword = passwordEncoder.encode(request.password());

        User user = new User(null, request.email(), hashedPassword, request.name(), null, null);
        User savedUser = createUserUseCase.execute(user);

        String accessToken = jwtService.generateAccessToken(savedUser.uuid(), savedUser.email());
        String refreshToken = jwtService.generateRefreshToken(savedUser.uuid(), savedUser.email());

        var authResponse = new AuthResponse(accessToken, refreshToken, UserResponseMapper.toResponse(savedUser));
        return ResponseEntity.status(HttpStatus.CREATED).body(authResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticates a user and returns authentication tokens")
    @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid credentials",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(userDetails.getUuid(), userDetails.getUsername());
        String refreshToken = jwtService.generateRefreshToken(userDetails.getUuid(), userDetails.getUsername());

        var userResponse = new com.lifttrack.api.infrastructure.rest.dto.response.UserResponse(
                userDetails.getUuid(),
                userDetails.getUsername(),
                userDetails.getName(),
                null);

        var authResponse = new AuthResponse(accessToken, refreshToken, userResponse);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token", description = "Uses a refresh token to obtain a new access token")
    @ApiResponse(responseCode = "200", description = "Token refreshed successfully",
            content = @Content(schema = @Schema(implementation = AuthResponse.class)))
    @ApiResponse(responseCode = "401", description = "Invalid or expired refresh token",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid or expired refresh token");
        }

        UUID userUuid = jwtService.extractUserUuid(refreshToken);
        String email = jwtService.extractEmail(refreshToken);

        String newAccessToken = jwtService.generateAccessToken(userUuid, email);

        var authResponse = new AuthResponse(newAccessToken, refreshToken, null);
        return ResponseEntity.ok(authResponse);
    }
}
