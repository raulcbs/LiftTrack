-- ============================================
-- Script de creación de base de datos
-- API de registro de entrenamientos
-- PostgreSQL
-- ============================================

-- Extensión para gen_random_uuid()
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
-- 2. MUSCLE_GROUPS (catálogo)
-- ============================================
CREATE TABLE muscle_groups (
    id          BIGSERIAL PRIMARY KEY,
    uuid        UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    name        VARCHAR(50) NOT NULL UNIQUE,
    created_at  TIMESTAMPTZ NOT NULL DEFAULT NOW()
);

-- Datos iniciales
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
-- 3. EXERCISES (catálogo maestro)
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
-- 5. ROUTINE_DAYS (días de la rutina)
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
-- 7. WORKOUT_SESSIONS (sesión real - histórico)
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
-- 10. PERSONAL_RECORDS (récords personales)
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

-- ============================================
-- COMENTARIOS DE COLUMNAS
-- Consultables con \d+ nombre_tabla en psql
-- ============================================

-- USERS
COMMENT ON TABLE users IS 'Usuarios registrados en la aplicación';
COMMENT ON COLUMN users.id IS 'Identificador interno autoincremental';
COMMENT ON COLUMN users.uuid IS 'Identificador público expuesto en la API';
COMMENT ON COLUMN users.email IS 'Email del usuario, usado para login';
COMMENT ON COLUMN users.password IS 'Hash de la contraseña (nunca texto plano)';
COMMENT ON COLUMN users.name IS 'Nombre visible del usuario';
COMMENT ON COLUMN users.created_at IS 'Fecha de registro del usuario';
COMMENT ON COLUMN users.updated_at IS 'Última fecha de modificación del perfil';

-- MUSCLE_GROUPS
COMMENT ON TABLE muscle_groups IS 'Catálogo de grupos musculares disponibles';
COMMENT ON COLUMN muscle_groups.name IS 'Nombre del grupo muscular: espalda, biceps, pecho, triceps, cuadriceps, femoral, gemelos, core, hombro, gluteo';

-- EXERCISES
COMMENT ON TABLE exercises IS 'Catálogo maestro de ejercicios. Puede ser global (user_id NULL) o personalizado por usuario';
COMMENT ON COLUMN exercises.name IS 'Nombre del ejercicio. Ej: Jalón al pecho, Curl de bíceps';
COMMENT ON COLUMN exercises.muscle_group_id IS 'Grupo muscular principal que trabaja el ejercicio';
COMMENT ON COLUMN exercises.user_id IS 'NULL = ejercicio global del sistema, NOT NULL = ejercicio personalizado creado por el usuario';

-- ROUTINES
COMMENT ON TABLE routines IS 'Plantilla de rutina de entrenamiento. Un usuario puede tener varias rutinas activas simultáneamente';
COMMENT ON COLUMN routines.name IS 'Nombre de la rutina. Ej: Rutina Volumen, Rutina Definición';
COMMENT ON COLUMN routines.description IS 'Descripción opcional de la rutina';
COMMENT ON COLUMN routines.is_active IS 'Indica si la rutina está activa o archivada';

-- ROUTINE_DAYS
COMMENT ON TABLE routine_days IS 'Días de la semana asignados a una rutina con su configuración';
COMMENT ON COLUMN routine_days.day_of_week IS 'Día de la semana: 1=Lunes, 2=Martes, 3=Miércoles, 4=Jueves, 5=Viernes, 6=Sábado, 7=Domingo';
COMMENT ON COLUMN routine_days.name IS 'Nombre descriptivo del día. Ej: Espalda/Bíceps, Pecho/Tríceps';
COMMENT ON COLUMN routine_days.sort_order IS 'Orden de visualización del día dentro de la rutina';

-- ROUTINE_EXERCISES
COMMENT ON TABLE routine_exercises IS 'Ejercicios planificados para un día concreto de la rutina (plantilla)';
COMMENT ON COLUMN routine_exercises.target_sets IS 'Número de series objetivo. Ej: 3, 4';
COMMENT ON COLUMN routine_exercises.target_rep_range IS 'Rango de repeticiones objetivo. Ej: 8-10, 12-15, 6';
COMMENT ON COLUMN routine_exercises.sort_order IS 'Orden del ejercicio dentro del día. Ej: 1=primer ejercicio, 2=segundo';
COMMENT ON COLUMN routine_exercises.notes IS 'Notas del ejercicio en la plantilla. Ej: agarre supino, tempo 3-1-2';

-- WORKOUT_SESSIONS
COMMENT ON TABLE workout_sessions IS 'Sesión de entrenamiento real realizada. Cada entrada es un día que se ha entrenado (histórico inmutable)';
COMMENT ON COLUMN workout_sessions.routine_day_id IS 'Día de rutina asociado. NULL si es una sesión libre sin rutina';
COMMENT ON COLUMN workout_sessions.session_date IS 'Fecha en la que se realizó el entrenamiento';
COMMENT ON COLUMN workout_sessions.started_at IS 'Hora de inicio de la sesión';
COMMENT ON COLUMN workout_sessions.finished_at IS 'Hora de finalización de la sesión';
COMMENT ON COLUMN workout_sessions.notes IS 'Notas del día. Ej: me sentía cansado, buen día de entrenamiento';

-- WORKOUT_SESSION_EXERCISES
COMMENT ON TABLE workout_session_exercises IS 'Ejercicio realizado dentro de una sesión de entrenamiento (registro real)';
COMMENT ON COLUMN workout_session_exercises.sort_order IS 'Orden en el que se realizó el ejercicio durante la sesión';
COMMENT ON COLUMN workout_session_exercises.notes IS 'Notas sobre el ejercicio en esta sesión. Ej: molestia en el hombro';

-- WORKOUT_SESSION_EXERCISE_SETS
COMMENT ON TABLE workout_session_exercise_sets IS 'Serie real realizada en un ejercicio de una sesión. Aquí se registra el peso y repeticiones reales';
COMMENT ON COLUMN workout_session_exercise_sets.set_number IS 'Número de serie realizada: 1, 2, 3...';
COMMENT ON COLUMN workout_session_exercise_sets.reps IS 'Repeticiones reales realizadas en esta serie';
COMMENT ON COLUMN workout_session_exercise_sets.weight IS 'Peso real utilizado en kg';
COMMENT ON COLUMN workout_session_exercise_sets.rir IS 'Reps In Reserve: repeticiones que quedaban en reserva (0=fallo muscular, 2=podría haber hecho 2 más)';
COMMENT ON COLUMN workout_session_exercise_sets.is_warmup IS 'TRUE si es una serie de calentamiento, FALSE si es serie efectiva';

-- PERSONAL_RECORDS
COMMENT ON TABLE personal_records IS 'Récords personales por ejercicio. Tabla desnormalizada para consultas rápidas de PRs';
COMMENT ON COLUMN personal_records.record_type IS 'Tipo de récord: max_weight (peso máximo), max_reps (máximas repeticiones), max_volume (mayor volumen)';
COMMENT ON COLUMN personal_records.weight IS 'Peso del récord en kg';
COMMENT ON COLUMN personal_records.reps IS 'Repeticiones del récord';
COMMENT ON COLUMN personal_records.achieved_at IS 'Fecha en la que se consiguió el récord';
COMMENT ON COLUMN personal_records.workout_session_exercise_set_id IS 'Referencia a la serie exacta donde se consiguió el récord';
