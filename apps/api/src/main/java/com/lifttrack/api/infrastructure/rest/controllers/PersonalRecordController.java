package com.lifttrack.api.infrastructure.rest.controllers;

import com.lifttrack.api.application.usecases.personalrecord.CreatePersonalRecordUseCase;
import com.lifttrack.api.application.usecases.personalrecord.DeletePersonalRecordUseCase;
import com.lifttrack.api.application.usecases.personalrecord.GetPersonalRecordByUserExerciseAndTypeUseCase;
import com.lifttrack.api.application.usecases.personalrecord.GetPersonalRecordByUuidUseCase;
import com.lifttrack.api.application.usecases.personalrecord.GetPersonalRecordsByUserAndExerciseUseCase;
import com.lifttrack.api.application.usecases.personalrecord.GetPersonalRecordsByUserUseCase;
import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.infrastructure.rest.dto.mapper.PersonalRecordResponseMapper;
import com.lifttrack.api.infrastructure.rest.dto.request.CreatePersonalRecordRequest;
import com.lifttrack.api.infrastructure.rest.dto.response.ErrorResponse;
import com.lifttrack.api.infrastructure.rest.dto.response.PersonalRecordResponse;
import com.lifttrack.api.infrastructure.security.AuthenticatedUser;

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

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/personal-records")
@RequiredArgsConstructor
@Tag(name = "Personal Records", description = "Operations for managing personal records")
public class PersonalRecordController {

    private final CreatePersonalRecordUseCase createPersonalRecordUseCase;
    private final GetPersonalRecordsByUserUseCase getPersonalRecordsByUserUseCase;
    private final GetPersonalRecordsByUserAndExerciseUseCase getPersonalRecordsByUserAndExerciseUseCase;
    private final GetPersonalRecordByUserExerciseAndTypeUseCase getPersonalRecordByUserExerciseAndTypeUseCase;
    private final GetPersonalRecordByUuidUseCase getPersonalRecordByUuidUseCase;
    private final DeletePersonalRecordUseCase deletePersonalRecordUseCase;

    @PostMapping
    @Operation(summary = "Create a new personal record", description = "Records a new personal record for the authenticated user")
    @ApiResponse(responseCode = "201", description = "Personal record created successfully",
            content = @Content(schema = @Schema(implementation = PersonalRecordResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request body",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<PersonalRecordResponse> create(@Valid @RequestBody CreatePersonalRecordRequest request) {
        UUID userUuid = AuthenticatedUser.getUuid();
        var personalRecord = new PersonalRecord(null, userUuid, request.exerciseUuid(), request.recordType(),
                request.weight(), request.reps(), request.achievedAt(), request.workoutSessionExerciseSetUuid(), null);
        var created = createPersonalRecordUseCase.execute(personalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(PersonalRecordResponseMapper.toResponse(created));
    }

    @GetMapping
    @Operation(summary = "Get personal records",
            description = "Returns personal records filtered by optional exercise UUID and record type. "
                    + "If both exerciseUuid and recordType are provided, returns a single record. "
                    + "If only exerciseUuid is provided, returns all records for that exercise. "
                    + "If neither is provided, returns all records for the authenticated user.")
    @ApiResponse(responseCode = "200", description = "Personal records retrieved successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = PersonalRecordResponse.class))))
    @ApiResponse(responseCode = "404", description = "Personal record not found (when filtering by exercise and type)",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<?> getByUser(
            @Parameter(description = "Filter by exercise UUID") @RequestParam(required = false) UUID exerciseUuid,
            @Parameter(description = "Filter by record type (requires exerciseUuid)", example = "ONE_REP_MAX") @RequestParam(required = false) String recordType) {
        UUID userUuid = AuthenticatedUser.getUuid();
        if (exerciseUuid != null && recordType != null) {
            return getPersonalRecordByUserExerciseAndTypeUseCase.execute(userUuid, exerciseUuid, recordType)
                    .map(PersonalRecordResponseMapper::toResponse)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
        if (exerciseUuid != null) {
            var records = getPersonalRecordsByUserAndExerciseUseCase.execute(userUuid, exerciseUuid)
                    .stream()
                    .map(PersonalRecordResponseMapper::toResponse)
                    .toList();
            return ResponseEntity.ok(records);
        }
        var records = getPersonalRecordsByUserUseCase.execute(userUuid)
                .stream()
                .map(PersonalRecordResponseMapper::toResponse)
                .toList();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get a personal record by UUID", description = "Returns a single personal record by its unique identifier")
    @ApiResponse(responseCode = "200", description = "Personal record found",
            content = @Content(schema = @Schema(implementation = PersonalRecordResponse.class)))
    @ApiResponse(responseCode = "404", description = "Personal record not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<PersonalRecordResponse> getByUuid(@PathVariable UUID uuid) {
        var record = getPersonalRecordByUuidUseCase.execute(uuid);
        return ResponseEntity.ok(PersonalRecordResponseMapper.toResponse(record));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Delete a personal record", description = "Deletes a personal record by its unique identifier")
    @ApiResponse(responseCode = "204", description = "Personal record deleted successfully")
    @ApiResponse(responseCode = "404", description = "Personal record not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        deletePersonalRecordUseCase.execute(uuid);
        return ResponseEntity.noContent().build();
    }
}
