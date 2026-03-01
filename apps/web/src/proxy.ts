import { NextResponse } from "next/server"
import type { NextRequest } from "next/server"
import createIntlMiddleware from "next-intl/middleware"
import { routing } from "@/src/i18n/routing"

const PUBLIC_ROUTES = ["/login", "/register"]

const handleI18nRouting = createIntlMiddleware(routing)

export function proxy(request: NextRequest) {
  const response = handleI18nRouting(request)

  const { pathname } = request.nextUrl

  // Strip locale prefix to get the "logical" pathname for route matching
  const pathnameWithoutLocale = routing.locales.reduce(
    (path, locale) => path.replace(new RegExp(`^/${locale}(?=/|$)`), ""),
    pathname,
  )
  const normalizedPathname = pathnameWithoutLocale || "/"

  const hasAccessToken = request.cookies.has("access_token")
  const isPublicRoute = PUBLIC_ROUTES.some((route) =>
    normalizedPathname.startsWith(route),
  )

  // Resolve locale from the i18n response header
  const locale =
    response.headers.get("x-next-intl-locale") ?? routing.defaultLocale

  // Unauthenticated user trying to access protected route → redirect to login
  if (!hasAccessToken && !isPublicRoute) {
    const loginUrl = new URL(`/${locale}/login`, request.url)
    loginUrl.searchParams.set("from", pathname)
    return NextResponse.redirect(loginUrl)
  }

  // Authenticated user trying to access login/register → redirect to home
  if (hasAccessToken && isPublicRoute) {
    return NextResponse.redirect(new URL(`/${locale}`, request.url))
  }

  return response
}

export const config = {
  matcher: [
    /*
     * Match all request paths except:
     * - _next/static (static files)
     * - _next/image (image optimization files)
     * - favicon.ico, sitemap.xml, robots.txt (metadata files)
     * - api routes (if any)
     */
    "/((?!_next/static|_next/image|favicon.ico|sitemap.xml|robots.txt|api).*)",
  ],
}
