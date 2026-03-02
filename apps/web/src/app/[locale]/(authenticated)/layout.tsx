import { Button, Separator } from "@heroui/react"
import { LogOut } from "lucide-react"
import { redirect } from "next/navigation"
import { useTranslations } from "next-intl"
import { logoutAction } from "@/src/actions"

export default function AuthenticatedLayout({
  children,
}: Readonly<{
  children: React.ReactNode
}>) {
  const t = useTranslations("AuthLayout")

  async function handleLogout() {
    "use server"
    await logoutAction()
    redirect("/login")
  }

  return (
    <div className="flex min-h-screen flex-col">
      <header className="bg-background/95 supports-backdrop-filter:bg-background/60 sticky top-0 z-50 backdrop-blur">
        <div className="mx-auto flex h-14 max-w-7xl items-center justify-between px-4">
          <h1 className="text-lg font-semibold tracking-tight">
            {t("appName")}
          </h1>
          <form action={handleLogout}>
            <Button type="submit" variant="ghost" size="sm">
              <LogOut className="h-4 w-4" />
              {t("signOut")}
            </Button>
          </form>
        </div>
        <Separator />
      </header>
      <main className="mx-auto w-full max-w-7xl flex-1 px-4 py-6">
        {children}
      </main>
    </div>
  )
}
