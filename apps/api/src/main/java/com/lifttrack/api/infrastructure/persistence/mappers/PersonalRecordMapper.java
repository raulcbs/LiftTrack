package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.PersonalRecordEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseSetEntity;

public final class PersonalRecordMapper {

    private PersonalRecordMapper() {
    }

    public static PersonalRecord toDomain(PersonalRecordEntity entity) {
        return new PersonalRecord(
                entity.getUuid(),
                entity.getUser().getUuid(),
                entity.getExercise().getUuid(),
                entity.getRecordType(),
                entity.getWeight(),
                entity.getReps(),
                entity.getAchievedAt(),
                entity.getWorkoutSessionExerciseSet() != null
                        ? entity.getWorkoutSessionExerciseSet().getUuid()
                        : null,
                entity.getCreatedAt()
        );
    }

    public static PersonalRecordEntity toEntity(PersonalRecord domain, UserEntity user, ExerciseEntity exercise, WorkoutSessionExerciseSetEntity setEntity) {
        PersonalRecordEntity entity = new PersonalRecordEntity();
        entity.setUuid(domain.uuid());
        entity.setUser(user);
        entity.setExercise(exercise);
        entity.setRecordType(domain.recordType());
        entity.setWeight(domain.weight());
        entity.setReps(domain.reps());
        entity.setAchievedAt(domain.achievedAt());
        entity.setWorkoutSessionExerciseSet(setEntity);
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
