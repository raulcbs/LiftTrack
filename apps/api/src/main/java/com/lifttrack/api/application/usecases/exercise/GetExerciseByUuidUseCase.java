package com.lifttrack.api.application.usecases.exercise;

import com.lifttrack.api.domain.models.Exercise;
import com.lifttrack.api.domain.ports.ExerciseRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetExerciseByUuidUseCase {

    private final ExerciseRepository exerciseRepository;

    public Exercise execute(UUID uuid) {
        return exerciseRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Exercise not found: " + uuid));
    }
}
