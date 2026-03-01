import type { User } from "./user"

export interface AuthTokens {
  readonly accessToken: string
  readonly refreshToken: string
  readonly user: User
}

export interface RegisterUser {
  readonly email: string
  readonly password: string
  readonly name: string
}

export interface LoginCredentials {
  readonly email: string
  readonly password: string
}

export interface RefreshToken {
  readonly refreshToken: string
}
