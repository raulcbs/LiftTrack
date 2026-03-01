export interface PersonalRecord {
  readonly uuid: string
  readonly userUuid: string
  readonly exerciseUuid: string
  readonly recordType: string
  readonly weight: number | null
  readonly reps: number | null
  readonly achievedAt: string
  readonly workoutSessionExerciseSetUuid: string | null
  readonly createdAt: string
}

export interface CreatePersonalRecord {
  readonly exerciseUuid: string
  readonly recordType: string
  readonly weight?: number
  readonly reps?: number
  readonly achievedAt: string
  readonly workoutSessionExerciseSetUuid?: string
}
