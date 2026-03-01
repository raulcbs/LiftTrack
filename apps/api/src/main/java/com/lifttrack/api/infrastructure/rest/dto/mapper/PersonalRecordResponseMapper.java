package com.lifttrack.api.infrastructure.rest.dto.mapper;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.infrastructure.rest.dto.response.PersonalRecordResponse;

public final class PersonalRecordResponseMapper {

    private PersonalRecordResponseMapper() {
    }

    public static PersonalRecordResponse toResponse(PersonalRecord personalRecord) {
        return new PersonalRecordResponse(
                personalRecord.uuid(),
                personalRecord.userUuid(),
                personalRecord.exerciseUuid(),
                personalRecord.recordType(),
                personalRecord.weight(),
                personalRecord.reps(),
                personalRecord.achievedAt(),
                personalRecord.workoutSessionExerciseSetUuid(),
                personalRecord.createdAt());
    }
}
