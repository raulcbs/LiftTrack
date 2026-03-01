export interface RoutineExercise {
  readonly uuid: string
  readonly routineDayUuid: string
  readonly exerciseUuid: string
  readonly targetSets: number | null
  readonly targetRepRange: string | null
  readonly sortOrder: number
  readonly notes: string | null
  readonly createdAt: string
}

export interface CreateRoutineExercise {
  readonly exerciseUuid: string
  readonly targetSets?: number
  readonly targetRepRange?: string
  readonly sortOrder: number
  readonly notes?: string
}
