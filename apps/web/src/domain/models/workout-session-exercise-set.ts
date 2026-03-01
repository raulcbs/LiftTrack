export interface WorkoutSessionExerciseSet {
  readonly uuid: string
  readonly workoutSessionExerciseUuid: string
  readonly setNumber: number
  readonly reps: number
  readonly weight: number
  readonly rir: number | null
  readonly warmup: boolean
  readonly createdAt: string
}

export interface CreateWorkoutSessionExerciseSet {
  readonly setNumber: number
  readonly reps: number
  readonly weight: number
  readonly rir?: number
  readonly warmup: boolean
}
