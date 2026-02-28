package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.Routine;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineRepository {

    Routine save(Routine routine);

    Optional<Routine> findByUuid(UUID uuid);

    List<Routine> findByUserUuid(UUID userUuid);

    List<Routine> findActiveByUserUuid(UUID userUuid);

    void deleteByUuid(UUID uuid);
}
