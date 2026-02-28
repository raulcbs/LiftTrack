package com.lifttrack.api.application.usecases.routine;

import com.lifttrack.api.domain.ports.RoutineRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DeleteRoutineUseCase {

    private final RoutineRepository routineRepository;

    public void execute(UUID uuid) {
        routineRepository.deleteByUuid(uuid);
    }
}
