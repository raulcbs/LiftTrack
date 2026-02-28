package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.MuscleGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MuscleGroupRepository {

    Optional<MuscleGroup> findByUuid(UUID uuid);

    List<MuscleGroup> findAll();
}
