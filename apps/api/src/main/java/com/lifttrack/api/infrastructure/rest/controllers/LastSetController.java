package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.workoutsessionexerciseset.GetLastSetByUserAndExerciseUseCase;
import com.lifttrack.api.infrastructure.rest.dto.mapper.WorkoutSessionExerciseSetResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.WorkoutSessionExerciseSetResponse;
import com.lifttrack.api.infrastructure.security.AuthenticatedUser;

import io.swagger.v3.oas.annotations.Operation;
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

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/exercises/{exerciseUuid}/last-set")
@RequiredArgsConstructor
@Tag(name = "Last Set", description = "Retrieve the most recent set for a user and exercise")
public class LastSetController {

    private final GetLastSetByUserAndExerciseUseCase getLastSetByUserAndExerciseUseCase;

    @GetMapping
    @Operation(summary = "Get the last set performed", description = "Returns the most recent set for the authenticated user and specified exercise")
    @ApiResponse(responseCode = "200", description = "Last set found",
            content = @Content(schema = @Schema(implementation = WorkoutSessionExerciseSetResponse.class)))
    @ApiResponse(responseCode = "404", description = "No set found for this user and exercise",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<WorkoutSessionExerciseSetResponse> getLastSet(@PathVariable UUID exerciseUuid) {
        UUID userUuid = AuthenticatedUser.getUuid();
        return getLastSetByUserAndExerciseUseCase.execute(userUuid, exerciseUuid)
                .map(WorkoutSessionExerciseSetResponseMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
