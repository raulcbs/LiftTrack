package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseEntity;

public final class WorkoutSessionExerciseMapper {

    private WorkoutSessionExerciseMapper() {
    }

    public static WorkoutSessionExercise toDomain(WorkoutSessionExerciseEntity entity) {
        return new WorkoutSessionExercise(
                entity.getUuid(),
                entity.getWorkoutSession().getUuid(),
                entity.getExercise().getUuid(),
                entity.getSortOrder(),
                entity.getNotes(),
                entity.getCreatedAt()
        );
    }

    public static WorkoutSessionExerciseEntity toEntity(WorkoutSessionExercise domain, WorkoutSessionEntity workoutSession, ExerciseEntity exercise) {
        WorkoutSessionExerciseEntity entity = new WorkoutSessionExerciseEntity();
        entity.setUuid(domain.uuid());
        entity.setWorkoutSession(workoutSession);
        entity.setExercise(exercise);
        entity.setSortOrder(domain.sortOrder());
        entity.setNotes(domain.notes());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
