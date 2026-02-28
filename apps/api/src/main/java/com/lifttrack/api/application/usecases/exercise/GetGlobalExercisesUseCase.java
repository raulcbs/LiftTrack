package com.lifttrack.api.application.usecases.exercise;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.domain.ports.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetGlobalExercisesUseCase {

    private final ExerciseRepository exerciseRepository;

    public List<Exercise> execute() {
        return exerciseRepository.findGlobalExercises();
    }
}
