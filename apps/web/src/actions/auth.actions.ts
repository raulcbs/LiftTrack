"use server"

import { createAuthAdapter } from "@/src/infrastructure"
import { clearAuthCookies } from "@/src/infrastructure/http/http-client"
import type {
  RegisterUser,
  LoginCredentials,
  RefreshToken,
} from "@/src/domain/models"
import { toActionResult } from "./action-result"
import type { ActionResult } from "./action-result"

const adapter = createAuthAdapter()

export async function registerAction(data: RegisterUser) {
  return toActionResult(adapter.register(data))
}

export async function loginAction(data: LoginCredentials) {
  return toActionResult(adapter.login(data))
}

export async function logoutAction(): Promise<ActionResult<void>> {
  try {
    await clearAuthCookies()
    return { success: true, data: undefined }
  } catch (error) {
    return {
      success: false,
      error: {
        type: "UNKNOWN_ERROR",
        message: error instanceof Error ? error.message : "Failed to logout",
      },
    }
  }
}

export async function refreshAction(data: RefreshToken) {
  return toActionResult(adapter.refresh(data))
}
