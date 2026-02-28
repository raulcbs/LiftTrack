package com.lifttrack.api.application.usecases.workoutsession;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.domain.ports.WorkoutSessionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionsByUserUseCase {

    private final WorkoutSessionRepository workoutSessionRepository;

    public List<WorkoutSession> execute(UUID userUuid) {
        return workoutSessionRepository.findByUserUuid(userUuid);
    }
}
