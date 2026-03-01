"use server"

import { createMuscleGroupAdapter } from "@/src/infrastructure"
import { toActionResult } from "./action-result"

const adapter = createMuscleGroupAdapter()

export async function getAllMuscleGroupsAction() {
  return toActionResult(adapter.getAll())
}

export async function getMuscleGroupAction(uuid: string) {
  return toActionResult(adapter.getByUuid(uuid))
}
