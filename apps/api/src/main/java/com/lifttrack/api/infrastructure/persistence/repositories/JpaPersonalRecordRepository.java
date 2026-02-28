package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.PersonalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaPersonalRecordRepository extends JpaRepository<PersonalRecordEntity, Long> {

    Optional<PersonalRecordEntity> findByUuid(UUID uuid);

    List<PersonalRecordEntity> findByUserUuid(UUID userUuid);

    List<PersonalRecordEntity> findByUserUuidAndExerciseUuid(UUID userUuid, UUID exerciseUuid);

    Optional<PersonalRecordEntity> findByUserUuidAndExerciseUuidAndRecordType(UUID userUuid, UUID exerciseUuid, String recordType);

    void deleteByUuid(UUID uuid);
}
