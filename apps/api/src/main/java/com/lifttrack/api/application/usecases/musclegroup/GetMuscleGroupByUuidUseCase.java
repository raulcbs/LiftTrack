package com.lifttrack.api.application.usecases.musclegroup;

import com.lifttrack.api.domain.models.MuscleGroup;
import com.lifttrack.api.domain.ports.MuscleGroupRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetMuscleGroupByUuidUseCase {

    private final MuscleGroupRepository muscleGroupRepository;

    public MuscleGroup execute(UUID uuid) {
        return muscleGroupRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("MuscleGroup not found: " + uuid));
    }
}
