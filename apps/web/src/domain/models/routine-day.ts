export type DayOfWeek = 1 | 2 | 3 | 4 | 5 | 6 | 7

export interface RoutineDay {
  readonly uuid: string
  readonly routineUuid: string
  readonly dayOfWeek: DayOfWeek
  readonly name: string
  readonly sortOrder: number
  readonly createdAt: string
}

export interface CreateRoutineDay {
  readonly dayOfWeek: DayOfWeek
  readonly name: string
  readonly sortOrder: number
}
