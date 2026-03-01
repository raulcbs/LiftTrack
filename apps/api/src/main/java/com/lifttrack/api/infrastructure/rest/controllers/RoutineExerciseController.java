package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.routineexercise.CreateRoutineExerciseUseCase;
import com.lifttrack.api.application.usecases.routineexercise.DeleteRoutineExerciseUseCase;
import com.lifttrack.api.application.usecases.routineexercise.GetRoutineExerciseByUuidUseCase;
import com.lifttrack.api.application.usecases.routineexercise.GetRoutineExercisesByRoutineDayUseCase;
import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.infrastructure.rest.dto.mapper.RoutineExerciseResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateRoutineExerciseRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.RoutineExerciseResponse;

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
@RequestMapping("/api/v1/routine-days/{routineDayUuid}/exercises")
@RequiredArgsConstructor
@Tag(name = "Routine Exercises", description = "Operations for managing exercises within a routine day")
public class RoutineExerciseController {

    private final CreateRoutineExerciseUseCase createRoutineExerciseUseCase;
    private final GetRoutineExercisesByRoutineDayUseCase getRoutineExercisesByRoutineDayUseCase;
    private final GetRoutineExerciseByUuidUseCase getRoutineExerciseByUuidUseCase;
    private final DeleteRoutineExerciseUseCase deleteRoutineExerciseUseCase;

    @PostMapping
    @Operation(summary = "Add an exercise to a routine day", description = "Adds a new exercise to the specified routine day")
    @ApiResponse(responseCode = "201", description = "Routine exercise created successfully",
            content = @Content(schema = @Schema(implementation = RoutineExerciseResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RoutineExerciseResponse> create(
            @PathVariable UUID routineDayUuid,
            @Valid @RequestBody CreateRoutineExerciseRequest request) {
        var routineExercise = new RoutineExercise(null, routineDayUuid, request.exerciseUuid(), request.targetSets(),
                request.targetRepRange(), request.sortOrder(), request.notes(), null);
        var created = createRoutineExerciseUseCase.execute(routineExercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoutineExerciseResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get all exercises for a routine day", description = "Returns all exercises belonging to the specified routine day")
    @ApiResponse(responseCode = "200", description = "Routine exercises retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoutineExerciseResponse.class))))
    public ResponseEntity<List<RoutineExerciseResponse>> getByRoutineDay(@PathVariable UUID routineDayUuid) {
        var exercises = getRoutineExercisesByRoutineDayUseCase.execute(routineDayUuid)
                .stream()
                .map(RoutineExerciseResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a routine exercise by UUID", description = "Returns a single routine exercise by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Routine exercise found",
            content = @Content(schema = @Schema(implementation = RoutineExerciseResponse.class)))
    @ApiResponse(responseCode = "404", description = "Routine exercise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RoutineExerciseResponse> getByUuid(@PathVariable UUID uuid) {
        var routineExercise = getRoutineExerciseByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(RoutineExerciseResponseMapper.toResponse(routineExercise));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a routine exercise", description = "Removes an exercise from a routine day")
    @ApiResponse(responseCode = "204", description = "Routine exercise deleted successfully")
    @ApiResponse(responseCode = "404", description = "Routine exercise not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteRoutineExerciseUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
