"use client"

import { SubmitEventHandler, useState } from "react"
import { Loader2 } from "lucide-react"
import { useTranslations } from "next-intl"
import { Link, useRouter } from "@/src/i18n/navigation"
import { loginAction } from "@/src/actions"
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

export default function LoginPage() {
  const t = useTranslations("LoginPage")
  const tc = useTranslations("Common")
  const router = useRouter()
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [error, setError] = useState<string | null>(null)
  const [loading, setLoading] = useState(false)

  const handleSubmit: SubmitEventHandler = async (e) => {
    e.preventDefault()
    setError(null)
    setLoading(true)

    try {
      const result = await loginAction({ email, password })

      if (result.success) {
        router.push("/")
        return
      }
      setError(result.error.message)
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
                autoComplete="current-password"
                disabled={loading}
              />
            </div>
          </CardContent>
          <CardFooter className="flex flex-col gap-4">
            <Button type="submit" className="w-full" disabled={loading}>
              {loading && <Loader2 className="h-4 w-4 animate-spin" />}
              {t("submit")}
            </Button>
            <p className="text-muted-foreground text-center text-sm">
              {t("noAccount")}{" "}
              <Link
                href="/register"
                className="text-foreground font-medium underline-offset-4 hover:underline"
              >
                {t("signUp")}
              </Link>
            </p>
          </CardFooter>
        </form>
      </Card>
    </div>
  )
}
