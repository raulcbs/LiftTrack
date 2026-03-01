package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.musclegroup.GetAllMuscleGroupsUseCase;
import com.lifttrack.api.application.usecases.musclegroup.GetMuscleGroupByUuidUseCase;
import com.lifttrack.api.infrastructure.rest.dto.mapper.MuscleGroupResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.MuscleGroupResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/muscle-groups")
@RequiredArgsConstructor
@Tag(name = "Muscle Groups", description = "Read-only operations for muscle groups")
public class MuscleGroupController {

    private final GetAllMuscleGroupsUseCase getAllMuscleGroupsUseCase;
    private final GetMuscleGroupByUuidUseCase getMuscleGroupByUuidUseCase;

    @GetMapping
    @Operation(summary = "Get all muscle groups", description = "Returns a list of all available muscle groups")
    @ApiResponse(responseCode = "200", description = "Muscle groups retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = MuscleGroupResponse.class))))
    public ResponseEntity<List<MuscleGroupResponse>> getAll() {
        var muscleGroups = getAllMuscleGroupsUseCase.execute()
                .stream()
                .map(MuscleGroupResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(muscleGroups);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a muscle group by UUID", description = "Returns a single muscle group by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Muscle group found",
            content = @Content(schema = @Schema(implementation = MuscleGroupResponse.class)))
    @ApiResponse(responseCode = "404", description = "Muscle group not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<MuscleGroupResponse> getByUuid(@PathVariable UUID uuid) {
        var muscleGroup = getMuscleGroupByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(MuscleGroupResponseMapper.toResponse(muscleGroup));
    }
}
