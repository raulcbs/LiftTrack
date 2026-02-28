package com.lifttrack.api.application.usecases.workoutsession;

import com.lifttrack.api.domain.models.WorkoutSession;
import com.lifttrack.api.domain.ports.WorkoutSessionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkoutSessionUseCase {

    private final WorkoutSessionRepository workoutSessionRepository;

    public WorkoutSession execute(WorkoutSession workoutSession) {
        return workoutSessionRepository.save(workoutSession);
    }
}
