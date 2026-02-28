package com.lifttrack.api.application.usecases.workoutsession;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.domain.ports.WorkoutSessionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionsByUserAndDateRangeUseCase {

    private final WorkoutSessionRepository workoutSessionRepository;

    public List<WorkoutSession> execute(UUID userUuid, LocalDate from, LocalDate to) {
        return workoutSessionRepository.findByUserUuidAndDateRange(userUuid, from, to);
    }
}
