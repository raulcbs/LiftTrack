package com.lifttrack.api.application.usecases.routine;

import com.lifttrack.api.domain.models.Routine;
import com.lifttrack.api.domain.ports.RoutineRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetRoutineByUuidUseCase {

    private final RoutineRepository routineRepository;

    public Routine execute(UUID uuid) {
        return routineRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Routine not found: " + uuid));
    }
}
