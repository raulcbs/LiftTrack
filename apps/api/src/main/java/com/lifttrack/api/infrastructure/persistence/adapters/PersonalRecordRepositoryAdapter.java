package com.lifttrack.api.infrastructure.persistence.adapters;

import com.lifttrack.api.domain.models.PersonalRecord;
import com.lifttrack.api.domain.ports.PersonalRecordRepository;
import com.lifttrack.api.infrastructure.persistence.entities.ExerciseEntity;
import com.lifttrack.api.infrastructure.persistence.entities.UserEntity;
import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseSetEntity;
import com.lifttrack.api.infrastructure.persistence.mappers.PersonalRecordMapper;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaExerciseRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaPersonalRecordRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaUserRepository;
import com.lifttrack.api.infrastructure.persistence.repositories.JpaWorkoutSessionExerciseSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PersonalRecordRepositoryAdapter implements PersonalRecordRepository {

    private final JpaPersonalRecordRepository jpaPersonalRecordRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaExerciseRepository jpaExerciseRepository;
    private final JpaWorkoutSessionExerciseSetRepository jpaWorkoutSessionExerciseSetRepository;

    @Override
    @Transactional
    public PersonalRecord save(PersonalRecord personalRecord) {
        UserEntity user = jpaUserRepository.findByUuid(personalRecord.userUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "User not found: " + personalRecord.userUuid()));

        ExerciseEntity exercise = jpaExerciseRepository.findByUuid(personalRecord.exerciseUuid())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Exercise not found: " + personalRecord.exerciseUuid()));

        WorkoutSessionExerciseSetEntity setEntity = null;
        if (personalRecord.workoutSessionExerciseSetUuid() != null) {
            setEntity = jpaWorkoutSessionExerciseSetRepository
                    .findByUuid(personalRecord.workoutSessionExerciseSetUuid())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "WorkoutSessionExerciseSet not found: "
                                    + personalRecord.workoutSessionExerciseSetUuid()));
        }

        var entity = PersonalRecordMapper.toEntity(personalRecord, user, exercise, setEntity);
        return PersonalRecordMapper.toDomain(jpaPersonalRecordRepository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalRecord> findByUuid(UUID uuid) {
        return jpaPersonalRecordRepository.findByUuid(uuid)
                .map(PersonalRecordMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalRecord> findByUserUuid(UUID userUuid) {
        return jpaPersonalRecordRepository.findByUserUuid(userUuid)
                .stream()
                .map(PersonalRecordMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<PersonalRecord> findByUserUuidAndExerciseUuid(UUID userUuid, UUID exerciseUuid) {
        return jpaPersonalRecordRepository.findByUserUuidAndExerciseUuid(userUuid, exerciseUuid)
                .stream()
                .map(PersonalRecordMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PersonalRecord> findByUserUuidAndExerciseUuidAndRecordType(UUID userUuid, UUID exerciseUuid, String recordType) {
        return jpaPersonalRecordRepository
                .findByUserUuidAndExerciseUuidAndRecordType(userUuid, exerciseUuid, recordType)
                .map(PersonalRecordMapper::toDomain);
    }

    @Override
    @Transactional
    public void deleteByUuid(UUID uuid) {
        jpaPersonalRecordRepository.deleteByUuid(uuid);
    }
}
