package com.lifttrack.api.application.usecases.routineday;

import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.domain.ports.RoutineDayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GetRoutineDayByUuidUseCase {

    private final RoutineDayRepository routineDayRepository;

    public RoutineDay execute(UUID uuid) {
        return routineDayRepository.findByUuid(uuid)
                .orElseThrow(() -> new IllegalArgumentException("RoutineDay not found: " + uuid));
    }
}
