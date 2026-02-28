package com.lifttrack.api.application.usecases.workoutsessionexercise;

import com.lifttrack.api.domain.models.WorkoutSessionExercise;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetWorkoutSessionExercisesBySessionUseCase {

    private final WorkoutSessionExerciseRepository workoutSessionExerciseRepository;

    public List<WorkoutSessionExercise> execute(UUID workoutSessionUuid) {
        return workoutSessionExerciseRepository.findByWorkoutSessionUuid(workoutSessionUuid);
    }
}
