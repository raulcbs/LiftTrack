import type { ResultAsync } from "neverthrow"
import type { DomainError } from "@/src/domain/errors"

export interface ActionSuccess<T> {
  readonly success: true
  readonly data: T
}

export interface ActionError {
  readonly success: false
  readonly error: {
    readonly type: string
    readonly message: string
    readonly fields?: Record<string, string>
  }
}

export type ActionResult<T> = ActionSuccess<T> | ActionError

export async function toActionResult<T>(
  resultAsync: ResultAsync<T, DomainError>,
): Promise<ActionResult<T>> {
  const result = await resultAsync

  return result.match(
    (data): ActionResult<T> => ({ success: true, data }),
    (error): ActionResult<T> => ({
      success: false,
      error: {
        type: error.type,
        message: error.message,
        ...("fields" in error && error.fields ? { fields: error.fields } : {}),
      },
    }),
  )
}
