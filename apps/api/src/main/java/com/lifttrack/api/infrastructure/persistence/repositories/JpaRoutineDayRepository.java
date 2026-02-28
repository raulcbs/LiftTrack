package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.RoutineDayEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRoutineDayRepository extends JpaRepository<RoutineDayEntity, Long> {

    Optional<RoutineDayEntity> findByUuid(UUID uuid);

    List<RoutineDayEntity> findByRoutineUuidOrderBySortOrder(UUID routineUuid);

    void deleteByUuid(UUID uuid);
}
