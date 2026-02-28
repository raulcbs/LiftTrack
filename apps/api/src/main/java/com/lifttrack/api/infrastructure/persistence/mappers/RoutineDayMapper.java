package com.lifttrack.api.infrastructure.persistence.mappers;

import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineDayEntity;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineEntity;

public final class RoutineDayMapper {

    private RoutineDayMapper() {
    }

    public static RoutineDay toDomain(RoutineDayEntity entity) {
        return new RoutineDay(
                entity.getUuid(),
                entity.getRoutine().getUuid(),
                entity.getDayOfWeek(),
                entity.getName(),
                entity.getSortOrder(),
                entity.getCreatedAt());
    }

    public static RoutineDayEntity toEntity(RoutineDay domain, RoutineEntity routine) {
        RoutineDayEntity entity = new RoutineDayEntity();
        entity.setUuid(domain.uuid());
        entity.setRoutine(routine);
        entity.setDayOfWeek(domain.dayOfWeek());
        entity.setName(domain.name());
        entity.setSortOrder(domain.sortOrder());
        entity.setCreatedAt(domain.createdAt());
        return entity;
    }
}
