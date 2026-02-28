package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.MuscleGroup;
import com.lifttrack.api.domain.ports.MuscleGroupRepository;
import com.lifttrack.api.infrastructure.persistence.mappers.MuscleGroupMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaMuscleGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MuscleGroupRepositoryAdapter implements MuscleGroupRepository {

    private final JpaMuscleGroupRepository jpaMuscleGroupRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<MuscleGroup> findByUuid(UUID uuid) {
        return jpaMuscleGroupRepository.findByUuid(uuid)
                .map(MuscleGroupMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MuscleGroup> findAll() {
        return jpaMuscleGroupRepository.findAll()
                .stream()
                .map(MuscleGroupMapper::toDomain)
                .toList();
    }
}
