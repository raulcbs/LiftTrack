package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.routine.CreateRoutineUseCase;
import com.lifttrack.api.application.usecases.routine.DeleteRoutineUseCase;
import com.lifttrack.api.application.usecases.routine.GetActiveRoutinesByUserUseCase;
import com.lifttrack.api.application.usecases.routine.GetRoutineByUuidUseCase;
import com.lifttrack.api.application.usecases.routine.GetRoutinesByUserUseCase;
import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.infrastructure.rest.dto.mapper.RoutineResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateRoutineRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.RoutineResponse;
import com.lifttrack.api.infrastructure.security.AuthenticatedUser;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/routines")
@RequiredArgsConstructor
@Tag(name = "Routines", description = "Operations for managing user routines")
public class RoutineController {

    private final CreateRoutineUseCase createRoutineUseCase;
    private final GetRoutinesByUserUseCase getRoutinesByUserUseCase;
    private final GetActiveRoutinesByUserUseCase getActiveRoutinesByUserUseCase;
    private final GetRoutineByUuidUseCase getRoutineByUuidUseCase;
    private final DeleteRoutineUseCase deleteRoutineUseCase;

    @PostMapping
    @Operation(summary = "Create a new routine", description = "Creates a new workout routine for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Routine created successfully",
            content = @Content(schema = @Schema(implementation = RoutineResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RoutineResponse> create(@Valid @RequestBody CreateRoutineRequest request) {
        UUID userUuid = AuthenticatedUser.getUuid();
        var routine = new Routine(null, userUuid, request.name(), request.description(), request.active(), null, null);
        var created = createRoutineUseCase.execute(routine);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoutineResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get all routines", description = "Returns all routines belonging to the authenticated user")
    @ApiResponse(responseCode = "200", description = "Routines retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoutineResponse.class))))
    public ResponseEntity<List<RoutineResponse>> getByUser() {
        UUID userUuid = AuthenticatedUser.getUuid();
        var routines = getRoutinesByUserUseCase.execute(userUuid)
                .stream()
                .map(RoutineResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(routines);
    }

    @GetMapping("/active")
    @Operation(summary = "Get active routines", description = "Returns only active routines for the authenticated user")
    @ApiResponse(responseCode = "200", description = "Active routines retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoutineResponse.class))))
    public ResponseEntity<List<RoutineResponse>> getActiveByUser() {
        UUID userUuid = AuthenticatedUser.getUuid();
        var routines = getActiveRoutinesByUserUseCase.execute(userUuid)
                .stream()
                .map(RoutineResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(routines);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a routine by UUID", description = "Returns a single routine by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Routine found",
            content = @Content(schema = @Schema(implementation = RoutineResponse.class)))
    @ApiResponse(responseCode = "404", description = "Routine not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RoutineResponse> getByUuid(@PathVariable UUID uuid) {
        var routine = getRoutineByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(RoutineResponseMapper.toResponse(routine));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a routine", description = "Deletes a routine by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Routine deleted successfully")
    @ApiResponse(responseCode = "404", description = "Routine not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteRoutineUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
