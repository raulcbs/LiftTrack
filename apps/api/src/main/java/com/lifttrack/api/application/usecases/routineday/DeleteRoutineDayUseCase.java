package com.lifttrack.api.application.usecases.routineday;

import com.lifttrack.api.domain.ports.RoutineDayRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteRoutineDayUseCase {

    private final RoutineDayRepository routineDayRepository;

    public void execute(UUID uuid) {
        routineDayRepository.deleteByUuid(uuid);
    }
}
