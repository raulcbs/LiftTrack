export interface Exercise {
  readonly uuid: string
  readonly name: string
  readonly muscleGroupUuid: string
  readonly userUuid: string | null
  readonly createdAt: string
}

export interface CreateExercise {
  readonly name: string
  readonly muscleGroupUuid: string
}
