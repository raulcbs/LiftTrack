package com.lifttrack.api.application.usecases.workoutsession;

import com.lifttrack.api.domain.ports.WorkoutSessionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteWorkoutSessionUseCase {

    private final WorkoutSessionRepository workoutSessionRepository;

    public void execute(UUID uuid) {
        workoutSessionRepository.deleteByUuid(uuid);
    }
}
