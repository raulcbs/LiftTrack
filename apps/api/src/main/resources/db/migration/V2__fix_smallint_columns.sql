-- Fix SMALLINT columns to INTEGER to match JPA int/Integer type mappings
ALTER TABLE routine_days ALTER COLUMN day_of_week TYPE INTEGER;
ALTER TABLE routine_days ALTER COLUMN sort_order TYPE INTEGER;

ALTER TABLE routine_exercises ALTER COLUMN target_sets TYPE INTEGER;
ALTER TABLE routine_exercises ALTER COLUMN sort_order TYPE INTEGER;

ALTER TABLE workout_session_exercises ALTER COLUMN sort_order TYPE INTEGER;

ALTER TABLE workout_session_exercise_sets ALTER COLUMN set_number TYPE INTEGER;
ALTER TABLE workout_session_exercise_sets ALTER COLUMN reps TYPE INTEGER;
ALTER TABLE workout_session_exercise_sets ALTER COLUMN rir TYPE INTEGER;

ALTER TABLE personal_records ALTER COLUMN reps TYPE INTEGER;
