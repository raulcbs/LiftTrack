package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.WorkoutSession;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WorkoutSessionRepository {

    WorkoutSession save(WorkoutSession workoutSession);

    Optional<WorkoutSession> findByUuid(UUID uuid);

    List<WorkoutSession> findByUserUuid(UUID userUuid);

    List<WorkoutSession> findByUserUuidAndDateRange(UUID userUuid, LocalDate from, LocalDate to);

    void deleteByUuid(UUID uuid);
}
