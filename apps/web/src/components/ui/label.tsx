"use client"

import * as LabelPrimitive from "@radix-ui/react-label"
import { type ComponentProps } from "react"
import { cn } from "@/src/lib/utils"

interface LabelProps extends ComponentProps<typeof LabelPrimitive.Root> {}

export function Label({ className, ...props }: LabelProps) {
  return (
    <LabelPrimitive.Root
      className={cn(
        "text-sm leading-none font-medium peer-disabled:cursor-not-allowed peer-disabled:opacity-70",
        className,
      )}
      {...props}
    />
  )
}
