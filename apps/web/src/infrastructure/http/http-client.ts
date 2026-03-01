import { ResultAsync } from "neverthrow"
import { cookies } from "next/headers"
import type { DomainError } from "@/src/domain/errors"
import { createDomainError } from "@/src/domain/errors"

const ACCESS_TOKEN_COOKIE = "access_token"
const REFRESH_TOKEN_COOKIE = "refresh_token"

const ACCESS_TOKEN_MAX_AGE = 900 // 15 minutes
const REFRESH_TOKEN_MAX_AGE = 604800 // 7 days

function getApiUrl(): string {
  const url = process.env.API_URL
  if (!url) {
    throw new Error("API_URL environment variable is not set")
  }
  return url
}

interface ApiErrorResponse {
  status: number
  message: string
  timestamp: string
}

function mapHttpStatusToDomainError(
  status: number,
  body: ApiErrorResponse | null,
): DomainError {
  const message = body?.message ?? `HTTP error ${status}`

  switch (status) {
    case 400:
      return createDomainError("VALIDATION_ERROR", message)
    case 401:
      return createDomainError("UNAUTHORIZED", message)
    case 403:
      return createDomainError("FORBIDDEN", message)
    case 404:
      return createDomainError("NOT_FOUND", message)
    case 409:
      return createDomainError("CONFLICT", message)
    default:
      return createDomainError("UNKNOWN_ERROR", message)
  }
}

async function parseErrorBody(
  response: Response,
): Promise<ApiErrorResponse | null> {
  try {
    return (await response.json()) as ApiErrorResponse
  } catch {
    return null
  }
}

async function getAccessToken(): Promise<string | undefined> {
  const cookieStore = await cookies()
  return cookieStore.get(ACCESS_TOKEN_COOKIE)?.value
}

async function getRefreshToken(): Promise<string | undefined> {
  const cookieStore = await cookies()
  return cookieStore.get(REFRESH_TOKEN_COOKIE)?.value
}

export async function setAuthCookies(
  accessToken: string,
  refreshToken: string,
): Promise<void> {
  const cookieStore = await cookies()
  const isProduction = process.env.NODE_ENV === "production"

  cookieStore.set(ACCESS_TOKEN_COOKIE, accessToken, {
    httpOnly: true,
    secure: isProduction,
    sameSite: "lax",
    path: "/",
    maxAge: ACCESS_TOKEN_MAX_AGE,
  })

  cookieStore.set(REFRESH_TOKEN_COOKIE, refreshToken, {
    httpOnly: true,
    secure: isProduction,
    sameSite: "lax",
    path: "/",
    maxAge: REFRESH_TOKEN_MAX_AGE,
  })
}

export async function clearAuthCookies(): Promise<void> {
  const cookieStore = await cookies()
  cookieStore.delete(ACCESS_TOKEN_COOKIE)
  cookieStore.delete(REFRESH_TOKEN_COOKIE)
}

interface RefreshResponse {
  accessToken: string
  refreshToken: string
  user: null
}

async function attemptTokenRefresh(): Promise<string | null> {
  const refreshToken = await getRefreshToken()
  if (!refreshToken) {
    return null
  }

  try {
    const response = await fetch(`${getApiUrl()}/api/v1/auth/refresh`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ refreshToken }),
    })

    if (!response.ok) {
      await clearAuthCookies()
      return null
    }

    const data = (await response.json()) as RefreshResponse
    await setAuthCookies(data.accessToken, data.refreshToken)
    return data.accessToken
  } catch {
    return null
  }
}

async function executeRequest(
  path: string,
  options: RequestInit,
  authenticated: boolean,
): Promise<Response> {
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
    ...(options.headers as Record<string, string>),
  }

  if (authenticated) {
    const token = await getAccessToken()
    if (token) {
      headers["Authorization"] = `Bearer ${token}`
    }
  }

  return fetch(`${getApiUrl()}${path}`, {
    ...options,
    headers,
  })
}

function request<T>(
  path: string,
  options: RequestInit,
  authenticated: boolean,
): ResultAsync<T, DomainError> {
  return ResultAsync.fromPromise(
    (async (): Promise<T> => {
      let response = await executeRequest(path, options, authenticated)

      // Auto-refresh on 401 for authenticated requests
      if (response.status === 401 && authenticated) {
        const newToken = await attemptTokenRefresh()
        if (newToken) {
          response = await executeRequest(path, options, authenticated)
        }
      }

      if (!response.ok) {
        const errorBody = await parseErrorBody(response)
        throw mapHttpStatusToDomainError(response.status, errorBody)
      }

      // 204 No Content
      if (response.status === 204) {
        return undefined as T
      }

      return (await response.json()) as T
    })(),
    (error): DomainError => {
      // If it's already a DomainError (thrown from inside), return it
      if (
        typeof error === "object" &&
        error !== null &&
        "type" in error &&
        typeof (error as DomainError).type === "string"
      ) {
        return error as DomainError
      }

      return createDomainError(
        "NETWORK_ERROR",
        error instanceof Error ? error.message : "An unexpected error occurred",
      )
    },
  )
}

function requestNullable<T>(
  path: string,
  options: RequestInit,
  authenticated: boolean,
): ResultAsync<T | null, DomainError> {
  return ResultAsync.fromPromise(
    (async (): Promise<T | null> => {
      let response = await executeRequest(path, options, authenticated)

      if (response.status === 401 && authenticated) {
        const newToken = await attemptTokenRefresh()
        if (newToken) {
          response = await executeRequest(path, options, authenticated)
        }
      }

      if (response.status === 404) {
        return null
      }

      if (!response.ok) {
        const errorBody = await parseErrorBody(response)
        throw mapHttpStatusToDomainError(response.status, errorBody)
      }

      if (response.status === 204) {
        return null
      }

      return (await response.json()) as T
    })(),
    (error): DomainError => {
      if (
        typeof error === "object" &&
        error !== null &&
        "type" in error &&
        typeof (error as DomainError).type === "string"
      ) {
        return error as DomainError
      }

      return createDomainError(
        "NETWORK_ERROR",
        error instanceof Error ? error.message : "An unexpected error occurred",
      )
    },
  )
}

export const httpClient = {
  get<T>(path: string): ResultAsync<T, DomainError> {
    return request<T>(path, { method: "GET" }, true)
  },

  post<T>(path: string, body: unknown): ResultAsync<T, DomainError> {
    return request<T>(
      path,
      { method: "POST", body: JSON.stringify(body) },
      true,
    )
  },

  delete(path: string): ResultAsync<void, DomainError> {
    return request<void>(path, { method: "DELETE" }, true)
  },

  publicPost<T>(path: string, body: unknown): ResultAsync<T, DomainError> {
    return request<T>(
      path,
      { method: "POST", body: JSON.stringify(body) },
      false,
    )
  },

  getNullable<T>(path: string): ResultAsync<T | null, DomainError> {
    return requestNullable<T>(path, { method: "GET" }, true)
  },
}
