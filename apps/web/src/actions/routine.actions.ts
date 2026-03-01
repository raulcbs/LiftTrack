"use server"

import { createRoutineAdapter } from "@/src/infrastructure"
import type { CreateRoutine } from "@/src/domain/models"
import { toActionResult } from "./action-result"

const adapter = createRoutineAdapter()

export async function createRoutineAction(data: CreateRoutine) {
  return toActionResult(adapter.create(data))
}

export async function getAllRoutinesAction() {
  return toActionResult(adapter.getAll())
}

export async function getActiveRoutinesAction() {
  return toActionResult(adapter.getActive())
}

export async function getRoutineAction(uuid: string) {
  return toActionResult(adapter.getByUuid(uuid))
}

export async function deleteRoutineAction(uuid: string) {
  return toActionResult(adapter.delete(uuid))
}
