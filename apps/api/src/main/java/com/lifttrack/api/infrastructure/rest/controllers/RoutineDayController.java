package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.routineday.CreateRoutineDayUseCase;
import com.lifttrack.api.application.usecases.routineday.DeleteRoutineDayUseCase;
import com.lifttrack.api.application.usecases.routineday.GetRoutineDayByUuidUseCase;
import com.lifttrack.api.application.usecases.routineday.GetRoutineDaysByRoutineUseCase;
import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.infrastructure.rest.dto.mapper.RoutineDayResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreateRoutineDayRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.RoutineDayResponse;

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
@RequestMapping("/api/v1/routines/{routineUuid}/days")
@RequiredArgsConstructor
@Tag(name = "Routine Days", description = "Operations for managing days within a routine")
public class RoutineDayController {

    private final CreateRoutineDayUseCase createRoutineDayUseCase;
    private final GetRoutineDaysByRoutineUseCase getRoutineDaysByRoutineUseCase;
    private final GetRoutineDayByUuidUseCase getRoutineDayByUuidUseCase;
    private final DeleteRoutineDayUseCase deleteRoutineDayUseCase;

    @PostMapping
    @Operation(summary = "Create a new routine day", description = "Adds a new day to the specified routine")
    @ApiResponse(responseCode = "201", description = "Routine day created successfully",
            content = @Content(schema = @Schema(implementation = RoutineDayResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RoutineDayResponse> create(
            @PathVariable UUID routineUuid,
            @Valid @RequestBody CreateRoutineDayRequest request) {
        var routineDay = new RoutineDay(null, routineUuid, request.dayOfWeek(), request.name(), request.sortOrder(),
                null);
        var created = createRoutineDayUseCase.execute(routineDay);
        return ResponseEntity.status(HttpStatus.CREATED).body(RoutineDayResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get all days for a routine", description = "Returns all days belonging to the specified routine")
    @ApiResponse(responseCode = "200", description = "Routine days retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = RoutineDayResponse.class))))
    public ResponseEntity<List<RoutineDayResponse>> getByRoutine(@PathVariable UUID routineUuid) {
        var days = getRoutineDaysByRoutineUseCase.execute(routineUuid)
                .stream()
                .map(RoutineDayResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(days);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a routine day by UUID", description = "Returns a single routine day by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Routine day found",
            content = @Content(schema = @Schema(implementation = RoutineDayResponse.class)))
    @ApiResponse(responseCode = "404", description = "Routine day not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<RoutineDayResponse> getByUuid(@PathVariable UUID uuid) {
        var routineDay = getRoutineDayByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(RoutineDayResponseMapper.toResponse(routineDay));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a routine day", description = "Deletes a routine day by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Routine day deleted successfully")
    @ApiResponse(responseCode = "404", description = "Routine day not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deleteRoutineDayUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
