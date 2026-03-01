package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.workoutsessionexerciseset.CreateWorkoutSessionExerciseSetUseCase;
import com.lifttrack.api.application.usecases.workoutsessionexerciseset.DeleteWorkoutSessionExerciseSetUseCase;
import com.lifttrack.api.application.usecases.workoutsessionexerciseset.GetWorkoutSessionExerciseSetByUuidUseCase;
import com.lifttrack.api.application.usecases.workoutsessionexerciseset.GetWorkoutSessionExerciseSetsByExerciseUseCase;
import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.infrastructure.rest.dto.mapper.WorkoutSessionExerciseSetResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateWorkoutSessionExerciseSetRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionExerciseSetResponse;

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
@RequestMapping("/api/v1/workout-session-exercises/{workoutSessionExerciseUuid}/sets")
@RequiredArgsConstructor
@Tag(name = "Workout Session Exercise Sets", description = "Operations for managing sets within a workout session exercise")
public class WorkoutSessionExerciseSetController {

    private final CreateWorkoutSessionExerciseSetUseCase createWorkoutSessionExerciseSetUseCase;
    private final GetWorkoutSessionExerciseSetsByExerciseUseCase getWorkoutSessionExerciseSetsByExerciseUseCase;
    private final GetWorkoutSessionExerciseSetByUuidUseCase getWorkoutSessionExerciseSetByUuidUseCase;
    private final DeleteWorkoutSessionExerciseSetUseCase deleteWorkoutSessionExerciseSetUseCase;

    @PostMapping
    @Operation(summary = "Create a new set", description = "Records a new set for the specified workout session exercise")
    @ApiResponse(responseCode = "201", description = "Set created successfully",
            content = @Content(schema = @Schema(implementation = WorkoutSessionExerciseSetResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionExerciseSetResponse> create(
            @PathVariable UUID workoutSessionExerciseUuid,
            @Valid @RequestBody CreateWorkoutSessionExerciseSetRequest request) {
        var set = new WorkoutSessionExerciseSet(null, workoutSessionExerciseUuid, request.setNumber(), request.reps(),
                request.weight(), request.rir(), request.warmup(), null);
        var created = createWorkoutSessionExerciseSetUseCase.execute(set);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(WorkoutSessionExerciseSetResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get all sets for a workout session exercise", description = "Returns all sets for the specified workout session exercise")
    @ApiResponse(responseCode = "200", description = "Sets retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = WorkoutSessionExerciseSetResponse.class))))
    public ResponseEntity<List<WorkoutSessionExerciseSetResponse>> getByWorkoutSessionExercise(
            @PathVariable UUID workoutSessionExerciseUuid) {
        var sets = getWorkoutSessionExerciseSetsByExerciseUseCase.execute(workoutSessionExerciseUuid)
                .stream()
                .map(WorkoutSessionExerciseSetResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(sets);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a set by UUID", description = "Returns a single set by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Set found",
            content = @Content(schema = @Schema(implementation = WorkoutSessionExerciseSetResponse.class)))
    @ApiResponse(responseCode = "404", description = "Set not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionExerciseSetResponse> getByUuid(@PathVariable UUID uuid) {
        var set = getWorkoutSessionExerciseSetByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(WorkoutSessionExerciseSetResponseMapper.toResponse(set));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a set", description = "Deletes a set by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Set deleted successfully")
    @ApiResponse(responseCode = "404", description = "Set not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteWorkoutSessionExerciseSetUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
