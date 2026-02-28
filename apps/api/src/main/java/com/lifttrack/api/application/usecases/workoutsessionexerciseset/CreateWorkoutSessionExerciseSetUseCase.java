package com.lifttrack.api.application.usecases.workoutsessionexerciseset;

import com.lifttrack.api.domain.models.WorkoutSessionExerciseSet;
import com.lifttrack.api.domain.ports.WorkoutSessionExerciseSetRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateWorkoutSessionExerciseSetUseCase {

    private final WorkoutSessionExerciseSetRepository workoutSessionExerciseSetRepository;

    public WorkoutSessionExerciseSet execute(WorkoutSessionExerciseSet set) {
        return workoutSessionExerciseSetRepository.save(set);
    }
}
