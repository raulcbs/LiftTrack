package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.workoutsessionexercise.CreateWorkoutSessionExerciseUseCase;
import com.lifttrack.api.application.usecases.workoutsessionexercise.DeleteWorkoutSessionExerciseUseCase;
import com.lifttrack.api.application.usecases.workoutsessionexercise.GetWorkoutSessionExerciseByUuidUseCase;
import com.lifttrack.api.application.usecases.workoutsessionexercise.GetWorkoutSessionExercisesBySessionUseCase;
import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.infrastructure.rest.dto.mapper.WorkoutSessionExerciseResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateWorkoutSessionExerciseRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionExerciseResponse;

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
@RequestMapping("/api/v1/workout-sessions/{workoutSessionUuid}/exercises")
@RequiredArgsConstructor
@Tag(name = "Workout Session Exercises", description = "Operations for managing exercises within a workout session")
public class WorkoutSessionExerciseController {

    private final CreateWorkoutSessionExerciseUseCase createWorkoutSessionExerciseUseCase;
    private final GetWorkoutSessionExercisesBySessionUseCase getWorkoutSessionExercisesBySessionUseCase;
    private final GetWorkoutSessionExerciseByUuidUseCase getWorkoutSessionExerciseByUuidUseCase;
    private final DeleteWorkoutSessionExerciseUseCase deleteWorkoutSessionExerciseUseCase;

    @PostMapping
    @Operation(summary = "Add an exercise to a workout session", description = "Records a new exercise performed during the specified workout session")
    @ApiResponse(responseCode = "201", description = "Workout session exercise created successfully",
            content = @Content(schema = @Schema(implementation = WorkoutSessionExerciseResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionExerciseResponse> create(
            @PathVariable UUID workoutSessionUuid,
            @Valid @RequestBody CreateWorkoutSessionExerciseRequest request) {
        var workoutSessionExercise = new WorkoutSessionExercise(null, workoutSessionUuid, request.exerciseUuid(),
                request.sortOrder(), request.notes(), null);
        var created = createWorkoutSessionExerciseUseCase.execute(workoutSessionExercise);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WorkoutSessionExerciseResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get all exercises for a workout session", description = "Returns all exercises performed in the specified workout session")
    @ApiResponse(responseCode = "200", description = "Workout session exercises retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = WorkoutSessionExerciseResponse.class))))
    public ResponseEntity<List<WorkoutSessionExerciseResponse>> getBySession(
            @PathVariable UUID workoutSessionUuid) {
        var exercises = getWorkoutSessionExercisesBySessionUseCase.execute(workoutSessionUuid)
                .stream()
                .map(WorkoutSessionExerciseResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a workout session exercise by UUID", description = "Returns a single workout session exercise by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Workout session exercise found",
            content = @Content(schema = @Schema(implementation = WorkoutSessionExerciseResponse.class)))
    @ApiResponse(responseCode = "404", description = "Workout session exercise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionExerciseResponse> getByUuid(@PathVariable UUID uuid) {
        var exercise = getWorkoutSessionExerciseByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(WorkoutSessionExerciseResponseMapper.toResponse(exercise));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a workout session exercise", description = "Removes an exercise from a workout session")
    @ApiResponse(responseCode = "204", description = "Workout session exercise deleted successfully")
    @ApiResponse(responseCode = "404", description = "Workout session exercise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteWorkoutSessionExerciseUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
