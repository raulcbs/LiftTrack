-- ============================================
-- V1: Schema inicial + datos semilla
-- API de registro de entrenamientos
-- PostgreSQL
-- ============================================

-- Extension para gen_random_uuid()
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ============================================
-- 1. USERS
-- ============================================
CREATE TABLE users (
    id          BIGSERIAL PRIMARY KEY,
    uuid        UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    name        VARCHAR(100) NOT NULL,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- ============================================
-- 2. MUSCLE_GROUPS (catalogo)
-- ============================================
CREATE TABLE muscle_groups (
    id          BIGSERIAL PRIMARY KEY,
    uuid        UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    name        VARCHAR(50) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

INSERT INTO muscle_groups (name) VALUES
    ('espalda'),
    ('biceps'),
    ('pecho'),
    ('triceps'),
    ('cuadriceps'),
    ('femoral'),
    ('gemelos'),
    ('core'),
    ('hombro'),
    ('gluteo');

-- ============================================
-- 3. EXERCISES (catalogo maestro)
-- ============================================
CREATE TABLE exercises (
    id              BIGSERIAL PRIMARY KEY,
    uuid            UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    name            VARCHAR(150) NOT NULL,
    muscle_group_id BIGINT NOT NULL REFERENCES muscle_groups(id),
    user_id         BIGINT REFERENCES users(id),
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_exercises_muscle_group ON exercises(muscle_group_id);
CREATE INDEX idx_exercises_user ON exercises(user_id);

-- ============================================
-- 4. ROUTINES (plantilla de rutina)
-- ============================================
CREATE TABLE routines (
    id          BIGSERIAL PRIMARY KEY,
    uuid        UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    user_id     BIGINT NOT NULL REFERENCES users(id),
    name        VARCHAR(150) NOT NULL,
    description TEXT,
    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_routines_user ON routines(user_id);

-- ============================================
-- 5. ROUTINE_DAYS (dias de la rutina)
-- ============================================
CREATE TABLE routine_days (
    id          BIGSERIAL PRIMARY KEY,
    uuid        UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    routine_id  BIGINT NOT NULL REFERENCES routines(id) ON DELETE CASCADE,
    day_of_week SMALLINT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),
    name        VARCHAR(100),
    sort_order  SMALLINT NOT NULL DEFAULT 0,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(routine_id, day_of_week)
);

CREATE INDEX idx_routine_days_routine ON routine_days(routine_id);

-- ============================================
-- 6. ROUTINE_EXERCISES (ejercicios planificados)
-- ============================================
CREATE TABLE routine_exercises (
    id              BIGSERIAL PRIMARY KEY,
    uuid            UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    routine_day_id  BIGINT NOT NULL REFERENCES routine_days(id) ON DELETE CASCADE,
    exercise_id     BIGINT NOT NULL REFERENCES exercises(id),
    target_sets     SMALLINT,
    target_rep_range VARCHAR(10),
    sort_order      SMALLINT NOT NULL DEFAULT 0,
    notes           TEXT,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_routine_exercises_day ON routine_exercises(routine_day_id);

-- ============================================
-- 7. WORKOUT_SESSIONS (sesion real - historico)
-- ============================================
CREATE TABLE workout_sessions (
    id              BIGSERIAL PRIMARY KEY,
    uuid            UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    user_id         BIGINT NOT NULL REFERENCES users(id),
    routine_day_id  BIGINT REFERENCES routine_days(id),
    session_date    DATE NOT NULL,
    started_at      TIMESTAMPTZ,
    finished_at     TIMESTAMPTZ,
    notes           TEXT,
    created_at      TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_workout_sessions_user_date ON workout_sessions(user_id, session_date);

-- ============================================
-- 8. WORKOUT_SESSION_EXERCISES (ejercicio realizado)
-- ============================================
CREATE TABLE workout_session_exercises (
    id                  BIGSERIAL PRIMARY KEY,
    uuid                UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    workout_session_id  BIGINT NOT NULL REFERENCES workout_sessions(id) ON DELETE CASCADE,
    exercise_id         BIGINT NOT NULL REFERENCES exercises(id),
    sort_order          SMALLINT NOT NULL DEFAULT 0,
    notes               TEXT,
    created_at          TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_workout_session_exercises_session ON workout_session_exercises(workout_session_id);

-- ============================================
-- 9. WORKOUT_SESSION_EXERCISE_SETS (serie real)
-- ============================================
CREATE TABLE workout_session_exercise_sets (
    id                          BIGSERIAL PRIMARY KEY,
    uuid                        UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    workout_session_exercise_id BIGINT NOT NULL REFERENCES workout_session_exercises(id) ON DELETE CASCADE,
    set_number                  SMALLINT NOT NULL,
    reps                        SMALLINT NOT NULL,
    weight                      DECIMAL(6,2) NOT NULL,
    rir                         SMALLINT,
    is_warmup                   BOOLEAN NOT NULL DEFAULT FALSE,
    created_at                  TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(workout_session_exercise_id, set_number)
);

CREATE INDEX idx_workout_session_exercise_sets_exercise ON workout_session_exercise_sets(workout_session_exercise_id);

-- ============================================
-- 10. PERSONAL_RECORDS (records personales)
-- ============================================
CREATE TABLE personal_records (
    id                              BIGSERIAL PRIMARY KEY,
    uuid                            UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    user_id                         BIGINT NOT NULL REFERENCES users(id),
    exercise_id                     BIGINT NOT NULL REFERENCES exercises(id),
    record_type                     VARCHAR(20) NOT NULL,
    weight                          DECIMAL(6,2),
    reps                            SMALLINT,
    achieved_at                     DATE NOT NULL,
    workout_session_exercise_set_id BIGINT REFERENCES workout_session_exercise_sets(id),
    created_at                      TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    UNIQUE(user_id, exercise_id, record_type)
);

CREATE INDEX idx_personal_records_user_exercise ON personal_records(user_id, exercise_id);
