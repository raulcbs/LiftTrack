export interface WorkoutSessionExercise {
  readonly uuid: string
  readonly workoutSessionUuid: string
  readonly exerciseUuid: string
  readonly sortOrder: number
  readonly notes: string | null
  readonly createdAt: string
}

export interface CreateWorkoutSessionExercise {
  readonly exerciseUuid: string
  readonly sortOrder: number
  readonly notes?: string
}
