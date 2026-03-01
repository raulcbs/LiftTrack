import type { ResultAsync } from "neverthrow"
import type {
  AuthTokens,
  RegisterUser,
  LoginCredentials,
  RefreshToken,
} from "../models"
import type { DomainError } from "../errors"

export interface AuthPort {
  register(data: RegisterUser): ResultAsync<AuthTokens, DomainError>
  login(data: LoginCredentials): ResultAsync<AuthTokens, DomainError>
  refresh(data: RefreshToken): ResultAsync<AuthTokens, DomainError>
}
