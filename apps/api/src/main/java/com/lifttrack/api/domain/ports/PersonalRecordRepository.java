package com.lifttrack.api.domain.ports;

import com.lifttrack.api.domain.models.PersonalRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonalRecordRepository {

    PersonalRecord save(PersonalRecord personalRecord);

    Optional<PersonalRecord> findByUuid(UUID uuid);

    List<PersonalRecord> findByUserUuid(UUID userUuid);

    List<PersonalRecord> findByUserUuidAndExerciseUuid(UUID userUuid, UUID exerciseUuid);

    Optional<PersonalRecord> findByUserUuidAndExerciseUuidAndRecordType(UUID userUuid, UUID exerciseUuid, String recordType);

    void deleteByUuid(UUID uuid);
}
