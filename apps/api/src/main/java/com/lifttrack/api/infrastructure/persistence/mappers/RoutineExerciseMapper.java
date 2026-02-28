package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.RoutineExercise;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineDayEntity;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineExerciseEntity;

public final class RoutineExerciseMapper {

    private RoutineExerciseMapper() {
    }

    public static RoutineExercise toDomain(RoutineExerciseEntity entity) {
        return new RoutineExercise(
                entity.getUuid(),
                entity.getRoutineDay().getUuid(),
                entity.getExercise().getUuid(),
                entity.getTargetSets(),
                entity.getTargetRepRange(),
                entity.getSortOrder(),
                entity.getNotes(),
                entity.getCreatedAt());
    }

    public static RoutineExerciseEntity toEntity(RoutineExercise domain, RoutineDayEntity routineDay,
            ExerciseEntity exercise) {
        RoutineExerciseEntity entity = new RoutineExerciseEntity();
        entity.setUuid(domain.uuid());
        entity.setRoutineDay(routineDay);
        entity.setExercise(exercise);
        entity.setTargetSets(domain.targetSets());
        entity.setTargetRepRange(domain.targetRepRange());
        entity.setSortOrder(domain.sortOrder());
        entity.setNotes(domain.notes());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
