package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineDayEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionEntity;

public final class WorkoutSessionMapper {

    private WorkoutSessionMapper() {
    }

    public static WorkoutSession toDomain(WorkoutSessionEntity entity) {
        return new WorkoutSession(
                entity.getUuid(),
                entity.getUser().getUuid(),
                entity.getRoutineDay() != null ? entity.getRoutineDay().getUuid() : null,
                entity.getSessionDate(),
                entity.getStartedAt(),
                entity.getFinishedAt(),
                entity.getNotes(),
                entity.getCreatedAt());
    }

    public static WorkoutSessionEntity toEntity(WorkoutSession domain, UserEntity user, RoutineDayEntity routineDay) {
        WorkoutSessionEntity entity = new WorkoutSessionEntity();
        entity.setUuid(domain.uuid());
        entity.setUser(user);
        entity.setRoutineDay(routineDay);
        entity.setSessionDate(domain.sessionDate());
        entity.setStartedAt(domain.startedAt());
        entity.setFinishedAt(domain.finishedAt());
        entity.setNotes(domain.notes());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
