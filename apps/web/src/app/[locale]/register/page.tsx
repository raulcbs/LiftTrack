"use client"

import { SubmitEventHandler, useState } from "react"
import { Loader2 } from "lucide-react"
import { useTranslations } from "next-intl"
import { Link, useRouter } from "@/src/i18n/navigation"
import { registerAction } from "@/src/actions"
import {
  Button,
  Input,
  Label,
  Card,
  CardHeader,
  CardTitle,
  CardDescription,
  CardContent,
  CardFooter,
} from "@/src/components/ui"

export default function RegisterPage() {
  const t = useTranslations("RegisterPage")
  const tc = useTranslations("Common")
  const router = useRouter()
  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [confirmPassword, setConfirmPassword] = useState("")
  const [error, setError] = useState<string | null>(null)
  const [fieldErrors, setFieldErrors] = useState<Record<string, string>>({})
  const [loading, setLoading] = useState(false)

  const handleSubmit: SubmitEventHandler = async (e) => {
    e.preventDefault()
    setError(null)
    setFieldErrors({})

    if (password !== confirmPassword) {
      setFieldErrors({ confirmPassword: t("passwordsMismatch") })
      return
    }

    setLoading(true)

    try {
      const result = await registerAction({ name, email, password })

      if (result.success) {
        router.push("/")
        return
      }

      if (result.error.fields) {
        setFieldErrors(result.error.fields)
      } else {
        setError(result.error.message)
      }
    } catch {
      setError(tc("unexpectedError"))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="flex min-h-screen items-center justify-center px-4">
      <Card className="w-full max-w-md">
        <CardHeader className="text-center">
          <CardTitle>{t("title")}</CardTitle>
          <CardDescription>{t("description")}</CardDescription>
        </CardHeader>
        <form onSubmit={handleSubmit}>
          <CardContent className="space-y-4">
            {error && (
              <div className="bg-destructive/10 text-destructive rounded-md px-4 py-3 text-sm">
                {error}
              </div>
            )}
            <div className="space-y-2">
              <Label htmlFor="name">{t("nameLabel")}</Label>
              <Input
                id="name"
                type="text"
                placeholder={t("namePlaceholder")}
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
                autoComplete="name"
                disabled={loading}
              />
              {fieldErrors.name && (
                <p className="text-destructive text-sm">{fieldErrors.name}</p>
              )}
            </div>
            <div className="space-y-2">
              <Label htmlFor="email">{t("emailLabel")}</Label>
              <Input
                id="email"
                type="email"
                placeholder={t("emailPlaceholder")}
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
                autoComplete="email"
                disabled={loading}
              />
              {fieldErrors.email && (
                <p className="text-destructive text-sm">{fieldErrors.email}</p>
              )}
            </div>
            <div className="space-y-2">
              <Label htmlFor="password">{t("passwordLabel")}</Label>
              <Input
                id="password"
                type="password"
                placeholder={t("passwordPlaceholder")}
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
                autoComplete="new-password"
                disabled={loading}
              />
              {fieldErrors.password && (
                <p className="text-destructive text-sm">
                  {fieldErrors.password}
                </p>
              )}
            </div>
            <div className="space-y-2">
              <Label htmlFor="confirmPassword">
                {t("confirmPasswordLabel")}
              </Label>
              <Input
                id="confirmPassword"
                type="password"
                placeholder={t("confirmPasswordPlaceholder")}
                value={confirmPassword}
                onChange={(e) => setConfirmPassword(e.target.value)}
                required
                autoComplete="new-password"
                disabled={loading}
              />
              {fieldErrors.confirmPassword && (
                <p className="text-destructive text-sm">
                  {fieldErrors.confirmPassword}
                </p>
              )}
            </div>
          </CardContent>
          <CardFooter className="flex flex-col gap-4">
            <Button type="submit" className="w-full" disabled={loading}>
              {loading && <Loader2 className="h-4 w-4 animate-spin" />}
              {t("submit")}
            </Button>
            <p className="text-muted-foreground text-center text-sm">
              {t("hasAccount")}{" "}
              <Link
                href="/login"
                className="text-foreground font-medium underline-offset-4 hover:underline"
              >
                {t("signIn")}
              </Link>
            </p>
          </CardFooter>
        </form>
      </Card>
    </div>
  )
}
