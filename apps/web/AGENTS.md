# AGENTS.md — LiftTrack Web

## Project Overview

- **Framework:** Next.js 16 (App Router, React 19, Server Components, Server Actions)
- **Language:** TypeScript 5 (strict mode) | **Styling:** Tailwind CSS 4
- **Architecture:** Hexagonal (Ports & Adapters) — domain layer has zero framework dependencies
- **Error handling:** `neverthrow` (`ResultAsync<T, DomainError>`) — never throw exceptions
- **i18n:** `next-intl` — locales `en` (default, no URL prefix), `es` (`/es/` prefix)
- **UI primitives:** Radix UI directly (not shadcn/ui) | **Icons:** Lucide React
- **Backend:** Spring Boot 4 REST API at `apps/api/` (see its own `AGENTS.md`)

## Build / Lint / Format Commands

All commands run from `apps/web/`.

```bash
npm run build          # next build (TypeScript checked, production build)
npm run dev            # next dev (local development server)
npm run lint           # eslint . (includes Prettier + neverthrow rules)
npm run lint:fix       # eslint . --fix
npm run format         # prettier --write .
npm run format:check   # prettier --check .
```

No test framework is configured yet. CI (`.github/workflows/web.yml`) runs `npm run lint` and `npm run build`.

### Docker (from monorepo root `/home/raulcbs/dev/liftTrack`)

```bash
docker compose -f docker-compose.db.yml up -d   # PostgreSQL only
docker compose up -d                              # full stack (db + api + web)
```

## Source Layout

```
src/
  domain/                     # Pure domain — NO framework imports
    models/                   # Readonly TypeScript interfaces
    ports/                    # Repository contracts (ResultAsync-based)
    errors/                   # DomainError discriminated union + factory
  infrastructure/
    http/
      http-client.ts          # Central HTTP client (neverthrow, auth cookies, auto-refresh)
      adapters/               # Port implementations via httpClient
  actions/                    # Next.js Server Actions ("use server")
    action-result.ts          # ResultAsync → serializable ActionResult<T> bridge
    *.actions.ts              # Thin wrappers: adapter.method() → toActionResult()
  components/ui/              # Reusable UI primitives (Button, Input, Card, Label)
  app/                        # Next.js App Router
    globals.css               # CSS variables + Tailwind v4 theme (light/dark)
    [locale]/                 # i18n locale segment
      (authenticated)/        # Route group for protected pages
  i18n/                       # next-intl config (routing, request, navigation)
  lib/utils.ts                # cn() utility (clsx + tailwind-merge)
  proxy.ts                    # Middleware: i18n routing + auth cookie guards
messages/                     # Translation JSON files (en.json, es.json)
patches/                      # patch-package patches (eslint-plugin-neverthrow fix)
```

## Code Style

### Formatting (Prettier)

- No semicolons, double quotes, trailing commas, 80-char line width
- 2-space indentation, LF line endings, Tailwind class sorting enabled

### TypeScript Conventions

- `interface` for all type definitions — never `type` aliases for object shapes
- All interface fields are `readonly`
- Named exports only — no default exports (except Next.js pages/layouts)
- Barrel `index.ts` at every module boundary; separate `export type` from value exports
- `import type { ... }` for type-only imports, always separate from value imports
- Path alias `@/src/...` for cross-module imports; relative `./` `../` within a module

### File Naming

- **All files:** kebab-case (`domain-errors.ts`, `http-client.ts`, `action-result.ts`)
- **Adapters:** `{entity}.adapter.ts` (`exercise.adapter.ts`)
- **Actions:** `{entity}.actions.ts` (`auth.actions.ts`)
- **Ports:** `{entity}.port.ts` (`exercise.port.ts`)
- **Components:** `{name}.tsx` (`button.tsx`, `card.tsx`)

### Naming Conventions

| Layer | Pattern | Example |
|---|---|---|
| Domain model | `interface {Name}` | `Exercise`, `WorkoutSession` |
| Create/write DTO | `interface Create{Name}` | `CreateExercise` |
| Domain port | `interface {Name}Port` | `ExercisePort`, `AuthPort` |
| Domain error | `interface {Name}Error` | `UnauthorizedError` |
| Adapter factory | `function create{Name}Adapter(): {Name}Port` | `createExerciseAdapter()` |
| Server Action | `async function {verb}{Name}Action(...)` | `createExerciseAction()` |
| UI component | `function {PascalCase}(props)` | `Button`, `CardHeader` |
| Component props | `interface {Name}Props extends ComponentProps<...>` | `ButtonProps` |

## Error Handling — Three-Layer Pattern

### 1. Domain errors (`src/domain/errors/`)

Discriminated union on `type` field. Seven variants: `UNAUTHORIZED`, `FORBIDDEN`, `NOT_FOUND`, `VALIDATION_ERROR`, `CONFLICT`, `NETWORK_ERROR`, `UNKNOWN_ERROR`. Created via `createDomainError(type, message)` factory.

### 2. Ports & adapters — neverthrow

Every port method returns `ResultAsync<T, DomainError>` — never throws. The `httpClient` wraps `fetch` in `ResultAsync.fromPromise()` and maps HTTP status codes to domain errors. ESLint rule `neverthrow/must-use-result` enforces all Results are consumed.

### 3. Server Actions — ActionResult bridge

`ResultAsync` is not serializable across the server/client boundary. Server Actions call `toActionResult(resultAsync)` to convert to a plain `ActionResult<T>` (discriminated union on `success: boolean`). Client components check `result.success` to access `result.data` or `result.error`.

**Data flow:** Client Component → Server Action → Adapter (implements Port) → httpClient → API

## Authentication

- JWT tokens stored in **httpOnly cookies** — never exposed to the browser
- `cookies()` from `next/headers` is async in Next.js 15+ (always `await`)
- Auto-refresh: httpClient retries with refreshed token on 401, with loop protection
- Logout: client-side cookie clearing (API has no logout endpoint)
- `API_URL` is a server-side env var (not `NEXT_PUBLIC_`)

## Component Conventions

- Radix UI primitives directly — not shadcn/ui
- Plain function components (no `forwardRef`, no classes)
- Props typed as `interface XxxProps extends ComponentProps<"element">` — empty interfaces allowed for semantic naming
- `cn()` from `@/src/lib/utils` for className merging
- Theme via CSS variables in HSL (`globals.css`), toggled by `prefers-color-scheme`

## i18n

- `next-intl` with `localePrefix: "as-needed"` — English gets clean URLs, Spanish gets `/es/`
- Translation files in `messages/{locale}.json`, organized by namespace (`LoginPage`, `Dashboard`, etc.)
- Components use `useTranslations("Namespace")` hook
- Navigation uses locale-aware `Link`, `useRouter`, `redirect` from `src/i18n/navigation`

## Key Dependencies

| Package | Purpose |
|---|---|
| `neverthrow` 8.x | Type-safe Result/ResultAsync error handling |
| `next-intl` 4.x | Internationalization |
| `@radix-ui/react-*` | Accessible UI primitives |
| `clsx` + `tailwind-merge` | Conditional + conflict-free class merging |
| `lucide-react` | Icons |
| `patch-package` | Patches `eslint-plugin-neverthrow` for ESLint 9 flat config |

## Things to Know

- The proxy/middleware file is `src/proxy.ts` (Next.js 16 convention), not `middleware.ts`
- `tsconfig.json` path alias: `@/*` → `./*`, so imports look like `@/src/domain/models`
- Adapters are stateless factory functions, instantiated at module scope in action files
- `httpClient.getNullable<T>()` returns `null` on 404 instead of an error
- Domain models use `string` for UUIDs and timestamps (not branded types or Date)
