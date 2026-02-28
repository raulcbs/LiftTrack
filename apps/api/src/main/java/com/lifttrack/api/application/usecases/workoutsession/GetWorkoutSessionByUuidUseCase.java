package com.lifttrack.api.application.usecases.workoutsession;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.domain.ports.WorkoutSessionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionByUuidUseCase {

    private final WorkoutSessionRepository workoutSessionRepository;

    public WorkoutSession execute(UUID uuid) {
        return workoutSessionRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("WorkoutSession not found: " + uuid));
    }
}
