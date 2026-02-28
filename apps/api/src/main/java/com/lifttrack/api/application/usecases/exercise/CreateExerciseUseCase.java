package com.lifttrack.api.application.usecases.exercise;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.domain.ports.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateExerciseUseCase {

    private final ExerciseRepository exerciseRepository;

    public Exercise execute(Exercise exercise) {
        return exerciseRepository.save(exercise);
    }
}
