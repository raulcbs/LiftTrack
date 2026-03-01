# AGENTS.md — LiftTrack API

## Project Overview

- **Language:** Java 21 | **Framework:** Spring Boot 4.0.3 | **Build:** Gradle 9.3.1 (Kotlin DSL)
- **Database:** PostgreSQL 16 | **ORM:** Spring Data JPA + Hibernate | **Migrations:** Flyway
- **Auth:** Spring Security 7 + JWT (JJWT 0.12.6) | **Docs:** SpringDoc OpenAPI 2.8.4
- **Architecture:** Hexagonal (Ports & Adapters) — domain layer has zero framework dependencies

## Build / Test / Run Commands

All commands run from `apps/api/`. Use `./gradlew` (not system Gradle).

```bash
./gradlew build          # compile + test + assemble
./gradlew test           # run all tests
./gradlew bootRun        # start the application (port 8080)
./gradlew clean build    # full clean rebuild
```

### Running a Single Test

```bash
./gradlew test --tests "com.lifttrack.api.SomeTest"             # single class
./gradlew test --tests "com.lifttrack.api.SomeTest.methodName"  # single method
./gradlew test --tests "*SomeTest"                              # pattern match
```

### Docker (from monorepo root)

```bash
docker compose -f docker-compose.db.yml up -d   # PostgreSQL only
docker compose up -d                              # full stack
```

### CI

GitHub Actions runs `gradle build` on pushes/PRs to `main` touching `apps/api/**`. No lint step. No DB in CI.

## Package Structure

```
com.lifttrack.api/
  LifttrackApiApplication.java
  domain/
    models/              # Java records — pure domain models
    ports/               # Repository interfaces (domain contracts)
  application/
    usecases/{entity}/   # Single-responsibility use case @Components
  infrastructure/
    persistence/
      entities/          # JPA @Entity classes (Lombok, @DynamicInsert)
      repositories/      # Spring Data JPA interfaces
      mappers/           # Static toDomain() / toEntity() utility classes
      adapters/          # Port implementations (@Component, @Transactional)
    rest/
      OpenApiConfig.java
      advice/            # GlobalExceptionHandler (@RestControllerAdvice)
      controllers/       # REST controllers (@RestController)
      dto/
        request/         # Request DTOs (records, Bean Validation + @Schema)
        response/        # Response DTOs (records, @Schema)
        mapper/          # Response mappers (static toResponse() utility classes)
    security/            # JWT auth (filter, service, config, user details)
```

## Naming Conventions

| Layer | Pattern | Example |
|---|---|---|
| Domain model | `{Name}` (record) | `User`, `WorkoutSession` |
| Domain port | `{Name}Repository` (interface) | `UserRepository` |
| Use case | `{Verb}{Name}UseCase` | `CreateExerciseUseCase` |
| JPA entity | `{Name}Entity` | `UserEntity` |
| JPA repository | `Jpa{Name}Repository` | `JpaUserRepository` |
| Persistence mapper | `{Name}Mapper` | `UserMapper` |
| Adapter | `{Name}RepositoryAdapter` | `UserRepositoryAdapter` |
| Controller | `{Name}Controller` | `ExerciseController` |
| Request DTO | `Create{Name}Request` | `CreateExerciseRequest` |
| Response DTO | `{Name}Response` | `ExerciseResponse` |
| Response mapper | `{Name}ResponseMapper` | `ExerciseResponseMapper` |

**Classes:** PascalCase. **Methods/fields:** camelCase. **Packages:** all lowercase.

## Import Ordering

Three groups separated by blank lines:

1. Project imports (`com.lifttrack.api.*`)
2. Third-party imports (`jakarta.*`, `lombok.*`, `org.hibernate.*`, `org.springframework.*`)
3. Java standard library (`java.*`)

Never use wildcard imports.

## Domain Models

- Immutable Java `record` types. No framework annotations.
- `create(...)` instance method validates inputs, passes `null` for auto-generated fields.
- Validation: sequential `if` checks throwing `IllegalArgumentException`.

## JPA Entities

- Class annotations in order: `@Entity`, `@Table(...)`, `@DynamicInsert`, `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`.
- `@DynamicInsert` on all entities — Hibernate omits null columns from INSERTs so PostgreSQL applies `DEFAULT gen_random_uuid()` and `DEFAULT NOW()`.
- Primary key: `Long id` with `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`.
- External ID: `UUID uuid` with `@Column(unique = true, updatable = false)`.
- Relationships: `@ManyToOne(fetch = FetchType.LAZY)` — never eager, never `@OneToMany`.
- Column mapping: `@Column(name = "snake_case")`. Booleans use `is_` prefix in DB.

## Persistence Mappers

- `public final class` with `private` constructor (utility class pattern).
- Two static methods: `toDomain(XxxEntity)` and `toEntity(Xxx domain, ...relatedEntities)`.
- Null-safe for optional relationships.

## Adapters

- `@Component` (not `@Repository` or `@Service`). Constructor injection via `@RequiredArgsConstructor`.
- `@Transactional(readOnly = true)` on reads; `@Transactional` on writes.
- Lookups: `.orElseThrow(() -> new IllegalArgumentException("Type not found: " + uuid))`.

## Use Cases

- `@Component @RequiredArgsConstructor`. One class per operation in `application/usecases/{entity}/`.
- Single public `execute(...)` method. Depends only on domain ports.

## Controllers

- `@RestController @RequestMapping("/api/v1/<resource>") @RequiredArgsConstructor @Tag(name = "...")`.
- Every method: `@Operation(summary, description)` + `@ApiResponse(responseCode, description, content)`.
- `@Valid @RequestBody` on POST endpoints. Lists use `@ArraySchema`.
- User UUID from JWT: `AuthenticatedUser.getUuid()` — never from path variables.
- Returns `ResponseEntity<XxxResponse>`. Deletes return `ResponseEntity<Void>` (204).
- Response mapping: `XxxResponseMapper.toResponse(domain)`.

## DTOs

**Request DTOs** — records with Bean Validation (`@NotBlank`, `@NotNull`, `@Email`, `@Size`, `@Min`) + `@Schema`.
**Response DTOs** — records with `@Schema` only. `ErrorResponse(int status, String message, Instant timestamp)`.
**Response Mappers** — `public final class` with `private` constructor. Static `toResponse()` method.

## Security (JWT)

- Stateless. CSRF disabled. Sessions: `STATELESS`.
- Public routes: `/api/v1/auth/**`, `/swagger-ui/**`, `/api-docs/**`.
- `JwtAuthenticationFilter` extracts Bearer token, validates, sets SecurityContext.
- `JwtService`: HMAC-SHA signing. Subject = userUuid. Claim `email`. Access: 15min, Refresh: 7days.
- `AuthenticatedUser`: static utility — `getUuid()`, `getEmail()` from SecurityContext.
- `CustomUserDetailsService` loads user by email via `UserRepository`.
- `DaoAuthenticationProvider(userDetailsService)` — Spring Security 7 requires constructor arg.
- `BCryptPasswordEncoder` for password hashing (done in AuthController, not domain layer).
- Auth endpoints: `POST /api/v1/auth/register`, `/login`, `/refresh`.

## Error Handling

`GlobalExceptionHandler` (`@RestControllerAdvice`) returns `ErrorResponse`:

| Exception | Status | Notes |
|---|---|---|
| `MethodArgumentNotValidException` | 400 | Joins field errors: `"field: message; ..."` |
| `IllegalArgumentException` | 404 if message contains "not found", else 400 | |
| `AuthenticationException` | 401 | `"Authentication failed: ..."` |
| `AccessDeniedException` | 403 | `"Access denied"` |
| `Exception` (catch-all) | 500 | `"An unexpected error occurred"` |

## Type Usage

| Use case | Type |
|---|---|
| Identifiers | `UUID` |
| Timestamps | `Instant` |
| Date-only | `LocalDate` |
| Weights/money | `BigDecimal` |
| Required int | `int` (primitive) |
| Nullable int | `Integer` (boxed) |
| Required bool | `boolean` (primitive) |

- `var` for mapper results. Explicit types when not obvious.
- `Optional` only as return type on `findBy*` methods — never as field/parameter.
- Prefer `.toList()` over `.collect(Collectors.toList())`.
- Text blocks (`"""..."""`) for multi-line JPQL.

## Dependency Injection

- Always constructor injection via `@RequiredArgsConstructor`. Never `@Autowired`.
- Domain ports have no Spring annotations.

## Code Style

- No linting/formatting tools (no Checkstyle, Spotless).
- Minimal comments — code should be self-documenting.
- No logging in the current codebase.
- JPA: `open-in-view: false`, `ddl-auto: validate` (Flyway manages schema).
- DB defaults: PostgreSQL generates `uuid`, `created_at`, `updated_at` via `DEFAULT` — Java never sets these.

## Key Files

- **Config:** `src/main/resources/application.yml`
- **Migrations:** `src/main/resources/db/migration/V1__init.sql`, `V2__fix_smallint_columns.sql`
- **API docs:** `/swagger-ui` (UI), `/api-docs` (OpenAPI spec)
- **Security:** `infrastructure/security/SecurityConfig.java`
- **JWT properties:** `jwt.secret`, `jwt.access-token-expiration`, `jwt.refresh-token-expiration` in YAML
