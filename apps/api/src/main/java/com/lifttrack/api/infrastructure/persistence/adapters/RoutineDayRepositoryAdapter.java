package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.RoutineDay;
import com.lifttrack.api.domain.ports.RoutineDayRepository;
import com.lifttrack.api.infrastructure.persistence.entities.RoutineEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.RoutineDayMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaRoutineDayRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaRoutineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RoutineDayRepositoryAdapter implements RoutineDayRepository {

    private final JpaRoutineDayRepository jpaRoutineDayRepository;
    private final JpaRoutineRepository jpaRoutineRepository;

    @Override
    @Transactional
    public RoutineDay save(RoutineDay routineDay) {
        RoutineEntity routine = jpaRoutineRepository.findByUuid(routineDay.routineUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Routine not found: " + routineDay.routineUuid()));

        var entity = RoutineDayMapper.toEntity(routineDay, routine);
        return RoutineDayMapper.toDomain(jpaRoutineDayRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoutineDay> findByUuid(UUID uuid) {
        return jpaRoutineDayRepository.findByUuid(uuid)
                .map(RoutineDayMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RoutineDay> findByRoutineUuid(UUID routineUuid) {
        return jpaRoutineDayRepository.findByRoutineUuidOrderBySortOrder(routineUuid)
                .stream()
                .map(RoutineDayMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaRoutineDayRepository.deleteByUuid(uuid);
    }
}
