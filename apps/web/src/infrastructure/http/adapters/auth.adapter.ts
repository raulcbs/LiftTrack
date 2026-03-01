import { ResultAsync } from "neverthrow"
import type { AuthPort } from "@/src/domain/ports"
import type {
  AuthTokens,
  RegisterUser,
  LoginCredentials,
  RefreshToken,
} from "@/src/domain/models"
import type { DomainError } from "@/src/domain/errors"
import { httpClient, setAuthCookies } from "../http-client"

interface AuthApiResponse {
  accessToken: string
  refreshToken: string
  user: {
    uuid: string
    email: string
    name: string
    createdAt: string | null
  }
}

function withCookiePersistence(
  result: ResultAsync<AuthApiResponse, DomainError>,
): ResultAsync<AuthTokens, DomainError> {
  return result.andThen((response) =>
    ResultAsync.fromPromise(
      setAuthCookies(response.accessToken, response.refreshToken).then(() => ({
        accessToken: response.accessToken,
        refreshToken: response.refreshToken,
        user: {
          uuid: response.user.uuid,
          email: response.user.email,
          name: response.user.name,
          createdAt: response.user.createdAt ?? "",
        },
      })),
      (error): DomainError => ({
        type: "UNKNOWN_ERROR",
        message:
          error instanceof Error
            ? error.message
            : "Failed to persist auth cookies",
      }),
    ),
  )
}

export function createAuthAdapter(): AuthPort {
  return {
    register(data: RegisterUser): ResultAsync<AuthTokens, DomainError> {
      return withCookiePersistence(
        httpClient.publicPost<AuthApiResponse>("/api/v1/auth/register", data),
      )
    },

    login(data: LoginCredentials): ResultAsync<AuthTokens, DomainError> {
      return withCookiePersistence(
        httpClient.publicPost<AuthApiResponse>("/api/v1/auth/login", data),
      )
    },

    refresh(data: RefreshToken): ResultAsync<AuthTokens, DomainError> {
      return withCookiePersistence(
        httpClient.publicPost<AuthApiResponse>("/api/v1/auth/refresh", data),
      )
    },
  }
}
