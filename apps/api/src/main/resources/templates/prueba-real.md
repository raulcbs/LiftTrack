# Simulacion real completa — 2 semanas de Raul

## FASE 1: Configuracion (se hace una sola vez)

### 1. users

```sql
INSERT INTO users (id, email, password, name) VALUES
(1, 'raul@email.com', '$2a$10$hash...', 'Raul');
```

### 2. muscle_groups — ya insertados con el script

```
id=1 espalda | id=2 biceps | id=3 pecho | id=4 triceps | id=5 cuadriceps
id=6 femoral | id=7 gemelos | id=8 core | id=9 hombro | id=10 gluteo
```

### 3. exercises — catalogo de ejercicios

```sql
INSERT INTO exercises (id, name, muscle_group_id, user_id) VALUES
(1,  'Jalon al pecho',              1, NULL),  -- espalda
(2,  'Curl de biceps con barra',    2, NULL),  -- biceps
(3,  'Press banca',                 3, NULL),  -- pecho
(4,  'Press frances mancuernas',    4, NULL),  -- triceps
(5,  'Sentadilla multipower',       5, NULL),  -- cuadriceps
(6,  'Peso muerto rumano',          6, NULL),  -- femoral
(7,  'Hip thrust',                  10, NULL), -- gluteo
(8,  'Press militar',               9, NULL),  -- hombro
(9,  'Rueda abdominal',             8, NULL),  -- core
-- Fullbody extras
(10, 'Dominadas',                   1, NULL),  -- espalda
(11, 'Press inclinado mancuernas',  3, NULL),  -- pecho
(12, 'Curl martillo',               2, NULL),  -- biceps
(13, 'Extension triceps polea',     4, NULL),  -- triceps
(14, 'Prensa de piernas',           5, NULL);  -- cuadriceps
```

### 4. routines — tu rutina

```sql
INSERT INTO routines (id, user_id, name, description, is_active) VALUES
(1, 1, 'Push/Pull/Leg', 'Rutina de volumen L-V', TRUE);
```

### 5. routine_days — tus 5 dias

```sql
INSERT INTO routine_days (id, routine_id, day_of_week, name, sort_order) VALUES
(1, 1, 1, 'Espalda/Biceps',    1),
(2, 1, 2, 'Pecho/Triceps',     2),
(3, 1, 3, 'Piernas',           3),
(4, 1, 4, 'Hombros y Core',    4),
(5, 1, 5, 'Fullbody',          5);
```

### 6. routine_exercises — que ejercicios y cuantas series/reps por dia

```sql
INSERT INTO routine_exercises (id, routine_day_id, exercise_id, target_sets, target_rep_range, sort_order) VALUES
-- Lunes: Espalda/Biceps
(1, 1, 1,  4, '8-10',  1),   -- Jalon al pecho: 4x8-10
(2, 1, 2,  3, '10-12', 2),   -- Curl biceps: 3x10-12

-- Martes: Pecho/Triceps
(3, 2, 3,  4, '6-8',   1),   -- Press banca: 4x6-8
(4, 2, 4,  3, '10-12', 2),   -- Press frances: 3x10-12

-- Miercoles: Piernas
(5, 3, 5,  4, '8-10',  1),   -- Sentadilla: 4x8-10
(6, 3, 6,  3, '10-12', 2),   -- Peso muerto rumano: 3x10-12
(7, 3, 7,  3, '10-12', 3),   -- Hip thrust: 3x10-12

-- Jueves: Hombros y Core
(8, 4, 8,  4, '8-10',  1),   -- Press militar: 4x8-10
(9, 4, 9,  3, '12-15', 2),   -- Rueda abdominal: 3x12-15

-- Viernes: Fullbody
(10, 5, 10, 3, '6-8',   1),  -- Dominadas: 3x6-8
(11, 5, 11, 3, '8-10',  2),  -- Press inclinado: 3x8-10
(12, 5, 12, 3, '10-12', 3),  -- Curl martillo: 3x10-12
(13, 5, 14, 3, '10-12', 4),  -- Prensa piernas: 3x10-12
(14, 5, 13, 3, '10-12', 5);  -- Extension triceps: 3x10-12
```

> Hasta aqui es la PLANTILLA. Se configura una vez y es lo que consultas cuando
> abres la app y dices "Que tengo que hacer hoy martes?"
>
> La app te muestra:
>
> ```
> MARTES -- Pecho/Triceps
>   1. Press banca          -> 4 series de 6-8 reps  (ultimo peso: 75kg)
>   2. Press frances manc.  -> 3 series de 10-12 reps (ultimo peso: 14kg)
> ```
>
> El "ultimo peso" sale de una query al historico (ver mas abajo).

---

## FASE 2: Semana 1 (2-6 marzo 2026) — Sesiones reales

Cada dia que entrenas, se crea una nueva fila en `workout_sessions`. Nunca se sobreescribe nada.

### Lunes 2 marzo — Espalda/Biceps

```sql
-- Sesion
INSERT INTO workout_sessions (id, user_id, routine_day_id, session_date, started_at, finished_at, notes) VALUES
(1, 1, 1, '2026-03-02', '2026-03-02 17:00', '2026-03-02 18:15', NULL);

-- Ejercicios realizados
INSERT INTO workout_session_exercises (id, workout_session_id, exercise_id, sort_order) VALUES
(1, 1, 1, 1),  -- Jalon al pecho
(2, 1, 2, 2);  -- Curl biceps

-- Series reales — Jalon al pecho
INSERT INTO workout_session_exercise_sets (workout_session_exercise_id, set_number, reps, weight, rir, is_warmup) VALUES
(1, 1, 12, 30.00,  NULL, TRUE),   -- calentamiento
(1, 2, 10, 50.00,  2,    FALSE),
(1, 3, 9,  55.00,  1,    FALSE),
(1, 4, 7,  55.00,  0,    FALSE);

-- Series reales — Curl biceps
INSERT INTO workout_session_exercise_sets (workout_session_exercise_id, set_number, reps, weight, rir, is_warmup) VALUES
(2, 1, 12, 10.00,  2, FALSE),
(2, 2, 10, 12.00,  1, FALSE),
(2, 3, 8,  12.00,  0, FALSE);
```

### Martes 3 marzo — Pecho/Triceps

```sql
INSERT INTO workout_sessions (id, user_id, routine_day_id, session_date, started_at, finished_at) VALUES
(2, 1, 2, '2026-03-03', '2026-03-03 17:00', '2026-03-03 18:20');

INSERT INTO workout_session_exercises (id, workout_session_id, exercise_id, sort_order) VALUES
(3, 2, 3, 1),  -- Press banca
(4, 2, 4, 2);  -- Press frances

-- Press banca
INSERT INTO workout_session_exercise_sets (workout_session_exercise_id, set_number, reps, weight, rir, is_warmup) VALUES
(3, 1, 10, 40.00, NULL, TRUE),
(3, 2, 8,  70.00, 2,    FALSE),
(3, 3, 7,  75.00, 1,    FALSE),
(3, 4, 6,  75.00, 0,    FALSE),
(3, 5, 5,  75.00, 0,    FALSE);

-- Press frances mancuernas
INSERT INTO workout_session_exercise_sets (workout_session_exercise_id, set_number, reps, weight, rir, is_warmup) VALUES
(4, 1, 12, 12.00, 2, FALSE),
(4, 2, 10, 14.00, 1, FALSE),
(4, 3, 9,  14.00, 0, FALSE);
```

*(Miercoles, jueves y viernes seguirian el mismo patron)*

---

## FASE 3: Semana 2 (9-13 marzo 2026) — SUBES PESO

Aqui es donde se ve la potencia del modelo. Es lunes otra vez, toca Espalda/Biceps.
Subes el jalon de 55kg a 57.5kg:

```sql
-- Nueva sesion — NUNCA tocas la de la semana pasada
INSERT INTO workout_sessions (id, user_id, routine_day_id, session_date, started_at, finished_at, notes) VALUES
(6, 1, 1, '2026-03-09', '2026-03-09 17:00', '2026-03-09 18:10', 'Me sentia fuerte hoy');

INSERT INTO workout_session_exercises (id, workout_session_id, exercise_id, sort_order) VALUES
(11, 6, 1, 1),  -- Jalon al pecho
(12, 6, 2, 2);  -- Curl biceps

-- Jalon al pecho — SUBIDA DE PESO: 55kg -> 57.5kg
INSERT INTO workout_session_exercise_sets (workout_session_exercise_id, set_number, reps, weight, rir, is_warmup) VALUES
(11, 1, 12, 30.00,  NULL, TRUE),
(11, 2, 10, 52.50,  2,    FALSE),  -- subio de 50 a 52.5
(11, 3, 8,  57.50,  1,    FALSE),  -- subio de 55 a 57.5
(11, 4, 6,  57.50,  0,    FALSE);  -- subio de 55 a 57.5

-- Curl biceps — mantiene peso
INSERT INTO workout_session_exercise_sets (workout_session_exercise_id, set_number, reps, weight, rir, is_warmup) VALUES
(12, 1, 12, 10.00,  2, FALSE),
(12, 2, 11, 12.00,  1, FALSE),  -- una rep mas que la semana pasada
(12, 3, 9,  12.00,  0, FALSE);  -- una rep mas que la semana pasada
```

Resultado: ahora tienes 2 sesiones de "Espalda/Biceps" en el historico, sin sobreescribir nada.

---

## Query clave: "Que peso use la ultima vez?"

Cuando abres la app el lunes y quieres ver que tienes que hacer, la query seria:

```sql
SELECT
    re.sort_order,
    e.name                AS ejercicio,
    re.target_sets,
    re.target_rep_range,
    last_session.last_weight,
    last_session.last_reps,
    last_session.session_date AS ultima_vez
FROM routine_exercises re
JOIN exercises e ON e.id = re.exercise_id
LEFT JOIN LATERAL (
    SELECT
        wses.weight   AS last_weight,
        wses.reps     AS last_reps,
        ws.session_date
    FROM workout_sessions ws
    JOIN workout_session_exercises wse ON wse.workout_session_id = ws.id
    JOIN workout_session_exercise_sets wses ON wses.workout_session_exercise_id = wse.id
    WHERE ws.user_id = 1
      AND wse.exercise_id = re.exercise_id
      AND wses.is_warmup = FALSE
    ORDER BY ws.session_date DESC, wses.weight DESC
    LIMIT 1
) last_session ON TRUE
WHERE re.routine_day_id = 1  -- Lunes
ORDER BY re.sort_order;
```

Resultado que veria Raul el lunes de la semana 3:

```
#  | Ejercicio        | Objetivo  | Ultimo peso | Ultimas reps | Ultima vez
---|------------------|-----------|-------------|--------------|------------
1  | Jalon al pecho   | 4x 8-10   | 57.50 kg    | 8            | 2026-03-09
2  | Curl biceps      | 3x 10-12  | 12.00 kg    | 9            | 2026-03-09
```

Asi sabe que la semana pasada uso 57.5kg en jalon y puede decidir si mantener o subir a 60kg.

---

## Queries utiles para estadisticas futuras

### Progresion de peso en un ejercicio (ej: press banca)

```sql
SELECT ws.session_date, MAX(wses.weight) AS peso_maximo
FROM workout_sessions ws
JOIN workout_session_exercises wse ON wse.workout_session_id = ws.id
JOIN workout_session_exercise_sets wses ON wses.workout_session_exercise_id = wse.id
WHERE ws.user_id = 1
  AND wse.exercise_id = 3  -- Press banca
  AND wses.is_warmup = FALSE
GROUP BY ws.session_date
ORDER BY ws.session_date;
```

### Volumen total semanal

```sql
SELECT
    DATE_TRUNC('week', ws.session_date) AS semana,
    SUM(wses.reps * wses.weight) AS volumen_total
FROM workout_sessions ws
JOIN workout_session_exercises wse ON wse.workout_session_id = ws.id
JOIN workout_session_exercise_sets wses ON wses.workout_session_exercise_id = wse.id
WHERE ws.user_id = 1
  AND wses.is_warmup = FALSE
GROUP BY DATE_TRUNC('week', ws.session_date)
ORDER BY semana;
```

### RIR medio por semana (para saber si entrenas demasiado cerca del fallo)

```sql
SELECT
    DATE_TRUNC('week', ws.session_date) AS semana,
    ROUND(AVG(wses.rir), 1) AS rir_medio
FROM workout_sessions ws
JOIN workout_session_exercises wse ON wse.workout_session_id = ws.id
JOIN workout_session_exercise_sets wses ON wses.workout_session_exercise_id = wse.id
WHERE ws.user_id = 1
  AND wses.is_warmup = FALSE
  AND wses.rir IS NOT NULL
GROUP BY DATE_TRUNC('week', ws.session_date)
ORDER BY semana;
```
