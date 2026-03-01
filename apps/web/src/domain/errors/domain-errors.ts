export interface UnauthorizedError {
  readonly type: "UNAUTHORIZED"
  readonly message: string
}

export interface ForbiddenError {
  readonly type: "FORBIDDEN"
  readonly message: string
}

export interface NotFoundError {
  readonly type: "NOT_FOUND"
  readonly message: string
}

export interface ValidationError {
  readonly type: "VALIDATION_ERROR"
  readonly message: string
  readonly fields?: Record<string, string>
}

export interface ConflictError {
  readonly type: "CONFLICT"
  readonly message: string
}

export interface NetworkError {
  readonly type: "NETWORK_ERROR"
  readonly message: string
}

export interface UnknownError {
  readonly type: "UNKNOWN_ERROR"
  readonly message: string
}

export type DomainError =
  | UnauthorizedError
  | ForbiddenError
  | NotFoundError
  | ValidationError
  | ConflictError
  | NetworkError
  | UnknownError

export function createDomainError<T extends DomainError["type"]>(
  type: T,
  message: string,
  fields?: Record<string, string>,
): Extract<DomainError, { type: T }> {
  const base = { type, message } as Extract<DomainError, { type: T }>
  if (type === "VALIDATION_ERROR" && fields) {
    return { ...base, fields } as Extract<DomainError, { type: T }>
  }
  return base
}
