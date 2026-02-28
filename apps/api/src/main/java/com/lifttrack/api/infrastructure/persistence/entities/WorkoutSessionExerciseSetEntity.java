package com.lifttrack.api.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "workout_session_exercise_sets", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"workout_session_exercise_id", "set_number"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutSessionExerciseSetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, updatable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_session_exercise_id", nullable = false)
    private WorkoutSessionExerciseEntity workoutSessionExercise;

    @Column(name = "set_number", nullable = false)
    private int setNumber;

    @Column(nullable = false)
    private int reps;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal weight;

    private Integer rir;

    @Column(name = "is_warmup", nullable = false)
    private boolean warmup;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}
