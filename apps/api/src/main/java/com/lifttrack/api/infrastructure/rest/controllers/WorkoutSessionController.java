package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.workoutsession.CreateWorkoutSessionUseCase;
import com.lifttrack.api.application.usecases.workoutsession.DeleteWorkoutSessionUseCase;
import com.lifttrack.api.application.usecases.workoutsession.GetWorkoutSessionByUuidUseCase;
import com.lifttrack.api.application.usecases.workoutsession.GetWorkoutSessionsByUserAndDateRangeUseCase;
import com.lifttrack.api.application.usecases.workoutsession.GetWorkoutSessionsByUserUseCase;
import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.infrastructure.rest.dto.mapper.WorkoutSessionResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateWorkoutSessionRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userUuid}/workout-sessions")
@RequiredArgsConstructor
@Tag(name = "Workout Sessions", description = "Operations for managing workout sessions")
public class WorkoutSessionController {

    private final CreateWorkoutSessionUseCase createWorkoutSessionUseCase;
    private final GetWorkoutSessionsByUserUseCase getWorkoutSessionsByUserUseCase;
    private final GetWorkoutSessionsByUserAndDateRangeUseCase getWorkoutSessionsByUserAndDateRangeUseCase;
    private final GetWorkoutSessionByUuidUseCase getWorkoutSessionByUuidUseCase;
    private final DeleteWorkoutSessionUseCase deleteWorkoutSessionUseCase;

    @PostMapping
    @Operation(summary = "Create a new workout session", description = "Creates a new workout session for the specified user")
    @ApiResponse(responseCode = "201", description = "Workout session created successfully",
            content = @Content(schema = @Schema(implementation = WorkoutSessionResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionResponse> create(
            @PathVariable UUID userUuid,
            @Valid @RequestBody CreateWorkoutSessionRequest request) {
        var workoutSession = new WorkoutSession(null, userUuid, request.routineDayUuid(), request.sessionDate(),
                request.startedAt(), request.finishedAt(), request.notes(), null);
        var created = createWorkoutSessionUseCase.execute(workoutSession);
        return ResponseEntity.status(HttpStatus.CREATED).body(WorkoutSessionResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get workout sessions for a user", description = "Returns all workout sessions for a user, optionally filtered by date range")
    @ApiResponse(responseCode = "200", description = "Workout sessions retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = WorkoutSessionResponse.class))))
    public ResponseEntity<List<WorkoutSessionResponse>> getByUser(
            @PathVariable UUID userUuid,
            @Parameter(description = "Start date for filtering (inclusive)", example = "2026-01-01")
            @RequestParam(required = false) LocalDate from,
            @Parameter(description = "End date for filtering (inclusive)", example = "2026-03-01")
            @RequestParam(required = false) LocalDate to) {
        List<WorkoutSession> sessions;
        if (from != null && to != null) {
            sessions = getWorkoutSessionsByUserAndDateRangeUseCase.execute(userUuid, from, to);
        } else {
            sessions = getWorkoutSessionsByUserUseCase.execute(userUuid);
        }
        var response = sessions.stream()
                .map(WorkoutSessionResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a workout session by UUID", description = "Returns a single workout session by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Workout session found",
            content = @Content(schema = @Schema(implementation = WorkoutSessionResponse.class)))
    @ApiResponse(responseCode = "404", description = "Workout session not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionResponse> getByUuid(@PathVariable UUID uuid) {
        var session = getWorkoutSessionByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(WorkoutSessionResponseMapper.toResponse(session));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a workout session", description = "Deletes a workout session by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Workout session deleted successfully")
    @ApiResponse(responseCode = "404", description = "Workout session not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteWorkoutSessionUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
