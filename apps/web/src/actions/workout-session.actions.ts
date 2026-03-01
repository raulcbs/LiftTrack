"use server"

import { createWorkoutSessionAdapter } from "@/src/infrastructure"
import type { CreateWorkoutSession } from "@/src/domain/models"
import type { DateRange } from "@/src/domain/ports"
import { toActionResult } from "./action-result"

const adapter = createWorkoutSessionAdapter()

export async function createWorkoutSessionAction(data: CreateWorkoutSession) {
  return toActionResult(adapter.create(data))
}

export async function getWorkoutSessionsAction(dateRange?: DateRange) {
  return toActionResult(adapter.getAll(dateRange))
}

export async function getWorkoutSessionAction(uuid: string) {
  return toActionResult(adapter.getByUuid(uuid))
}

export async function deleteWorkoutSessionAction(uuid: string) {
  return toActionResult(adapter.delete(uuid))
}
