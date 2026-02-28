package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.RoutineExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRoutineExerciseRepository extends JpaRepository<RoutineExerciseEntity, Long> {

    Optional<RoutineExerciseEntity> findByUuid(UUID uuid);

    List<RoutineExerciseEntity> findByRoutineDayUuidOrderBySortOrder(UUID routineDayUuid);

    void deleteByUuid(UUID uuid);
}
