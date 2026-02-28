package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseSetEntity;

public final class WorkoutSessionExerciseSetMapper {

    private WorkoutSessionExerciseSetMapper() {
    }

    public static WorkoutSessionExerciseSet toDomain(WorkoutSessionExerciseSetEntity entity) {
        return new WorkoutSessionExerciseSet(
                entity.getUuid(),
                entity.getWorkoutSessionExercise().getUuid(),
                entity.getSetNumber(),
                entity.getReps(),
                entity.getWeight(),
                entity.getRir(),
                entity.isWarmup(),
                entity.getCreatedAt());
    }

    public static WorkoutSessionExerciseSetEntity toEntity(WorkoutSessionExerciseSet domain, WorkoutSessionExerciseEntity workoutSessionExercise) {
        WorkoutSessionExerciseSetEntity entity = new WorkoutSessionExerciseSetEntity();
        entity.setUuid(domain.uuid());
        entity.setWorkoutSessionExercise(workoutSessionExercise);
        entity.setSetNumber(domain.setNumber());
        entity.setReps(domain.reps());
        entity.setWeight(domain.weight());
        entity.setRir(domain.rir());
        entity.setWarmup(domain.warmup());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
