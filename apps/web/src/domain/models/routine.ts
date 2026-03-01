export interface Routine {
  readonly uuid: string
  readonly userUuid: string
  readonly name: string
  readonly description: string
  readonly active: boolean
  readonly createdAt: string
  readonly updatedAt: string
}

export interface CreateRoutine {
  readonly name: string
  readonly description?: string
  readonly active: boolean
}
