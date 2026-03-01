export interface WorkoutSession {
  readonly uuid: string
  readonly userUuid: string
  readonly routineDayUuid: string | null
  readonly sessionDate: string
  readonly startedAt: string
  readonly finishedAt: string | null
  readonly notes: string | null
  readonly createdAt: string
}

export interface CreateWorkoutSession {
  readonly routineDayUuid?: string
  readonly sessionDate: string
  readonly startedAt: string
  readonly finishedAt?: string
  readonly notes?: string
}
