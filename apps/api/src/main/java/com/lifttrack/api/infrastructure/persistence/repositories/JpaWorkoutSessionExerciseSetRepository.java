package com.lifttrack.api.infrastructure.persistence.repositories;

import com.lifttrack.api.infrastructure.persistence.entities.WorkoutSessionExerciseSetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaWorkoutSessionExerciseSetRepository extends JpaRepository<WorkoutSessionExerciseSetEntity, Long> {

	Optional<WorkoutSessionExerciseSetEntity> findByUuid(UUID uuid);

	List<WorkoutSessionExerciseSetEntity> findByWorkoutSessionExerciseUuidOrderBySetNumber(
			UUID workoutSessionExerciseUuid);

	void deleteByUuid(UUID uuid);

	/**
	 * Finds the last (most recent, heaviest) non-warmup set for a given user and
	 * exercise.
	 */
	@Query("""
			SELECT s FROM WorkoutSessionExerciseSetEntity s
			JOIN s.workoutSessionExercise wse
			JOIN wse.workoutSession ws
			WHERE ws.user.uuid = :userUuid
			  AND wse.exercise.uuid = :exerciseUuid
			  AND s.warmup = false
			ORDER BY ws.sessionDate DESC, s.weight DESC
			LIMIT 1
			""")
	Optional<WorkoutSessionExerciseSetEntity> findLastByUserUuidAndExerciseUuid(
			@Param("userUuid") UUID userUuid,
			@Param("exerciseUuid") UUID exerciseUuid);
}
