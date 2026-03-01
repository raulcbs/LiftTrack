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
import org.hibernate.annotations.DynamicInsert;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "personal_records", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "exercise_id", "record_type"})
})
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonalRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

    @Column(name = "record_type", nullable = false, length = 20)
    private String recordType;

    @Column(precision = 6, scale = 2)
    private BigDecimal weight;

    private Integer reps;

    @Column(name = "achieved_at", nullable = false)
    private LocalDate achievedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_session_exercise_set_id")
    private WorkoutSessionExerciseSetEntity workoutSessionExerciseSet;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}
