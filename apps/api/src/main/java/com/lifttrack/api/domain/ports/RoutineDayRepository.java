package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.RoutineDay;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineDayRepository {

    RoutineDay save(RoutineDay routineDay);

    Optional<RoutineDay> findByUuid(UUID uuid);

    List<RoutineDay> findByRoutineUuid(UUID routineUuid);

    void deleteByUuid(UUID uuid);
}
