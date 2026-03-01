package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.exercise.CreateExerciseUseCase;
import com.lifttrack.api.application.usecases.exercise.DeleteExerciseUseCase;
import com.lifttrack.api.application.usecases.exercise.GetExerciseByUuidUseCase;
import com.lifttrack.api.application.usecases.exercise.GetExercisesByMuscleGroupUseCase;
import com.lifttrack.api.application.usecases.exercise.GetGlobalExercisesUseCase;
import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.infrastructure.rest.dto.mapper.ExerciseResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateExerciseRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.ExerciseResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exercises")
@RequiredArgsConstructor
@Tag(name = "Exercises", description = "Operations for managing exercises")
public class ExerciseController {

    private final CreateExerciseUseCase createExerciseUseCase;
    private final GetExerciseByUuidUseCase getExerciseByUuidUseCase;
    private final GetExercisesByMuscleGroupUseCase getExercisesByMuscleGroupUseCase;
    private final GetGlobalExercisesUseCase getGlobalExercisesUseCase;
    private final DeleteExerciseUseCase deleteExerciseUseCase;

    @PostMapping
    @Operation(summary = "Create a new exercise", description = "Creates a new exercise associated with a muscle group")
    @ApiResponse(responseCode = "201", description = "Exercise created successfully",
            content = @Content(schema = @Schema(implementation = ExerciseResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ExerciseResponse> create(@Valid @RequestBody CreateExerciseRequest request) {
        var exercise = new Exercise(null, request.name(), request.muscleGroupUuid(), request.userUuid(), null);
        var created = createExerciseUseCase.execute(exercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(ExerciseResponseMapper.toResponse(created));
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get an exercise by UUID", description = "Returns a single exercise by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Exercise found",
            content = @Content(schema = @Schema(implementation = ExerciseResponse.class)))
    @ApiResponse(responseCode = "404", description = "Exercise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<ExerciseResponse> getByUuid(@PathVariable UUID uuid) {
        var exercise = getExerciseByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(ExerciseResponseMapper.toResponse(exercise));
    }

    @GetMapping("/global")
    @Operation(summary = "Get all global exercises", description = "Returns all exercises that are not user-specific")
    @ApiResponse(responseCode = "200", description = "Global exercises retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExerciseResponse.class))))
    public ResponseEntity<List<ExerciseResponse>> getGlobal() {
        var exercises = getGlobalExercisesUseCase.execute()
                .stream()
                .map(ExerciseResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping
    @Operation(summary = "Get exercises by muscle group", description = "Returns all exercises for a given muscle group")
    @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = ExerciseResponse.class))))
    public ResponseEntity<List<ExerciseResponse>> getByMuscleGroup(@RequestParam UUID muscleGroupUuid) {
        var exercises = getExercisesByMuscleGroupUseCase.execute(muscleGroupUuid)
                .stream()
                .map(ExerciseResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(exercises);
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete an exercise", description = "Deletes an exercise by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Exercise deleted successfully")
    @ApiResponse(responseCode = "404", description = "Exercise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteExerciseUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
