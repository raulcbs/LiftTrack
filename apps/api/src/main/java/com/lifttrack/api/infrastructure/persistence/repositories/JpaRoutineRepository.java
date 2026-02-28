package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.RoutineEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaRoutineRepository extends JpaRepository<RoutineEntity, Long> {

    Optional<RoutineEntity> findByUuid(UUID uuid);

    List<RoutineEntity> findByUserUuid(UUID userUuid);

    List<RoutineEntity> findByUserUuidAndActiveTrue(UUID userUuid);

    void deleteByUuid(UUID uuid);
}
