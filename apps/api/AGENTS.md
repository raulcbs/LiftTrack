# AGENTS.md — LiftTrack API

## Project Overview

- **Language:** Java 21 | **Framework:** Spring Boot 4.0.3 | **Build:** Gradle 9.3.1 (Kotlin DSL)
- **Database:** PostgreSQL 16 | **ORM:** Spring Data JPA + Hibernate | **Migrations:** Flyway
- **Auth:** Spring Security + OAuth2 + JJWT 0.12.6 | **Docs:** SpringDoc OpenAPI 2.8.4
- **Architecture:** Hexagonal (Ports and Adapters) — domain layer has zero framework dependencies

## Build / Test / Run Commands

All commands run from `apps/api/`. Use `./gradlew` (not system Gradle).

```bash
./gradlew build          # compile + test + assemble
./gradlew test           # run all tests
./gradlew bootRun        # start the application (port 8080)
./gradlew bootJar        # build executable JAR
./gradlew clean build    # full clean rebuild
```

### Running a Single Test

```bash
# Single test class
./gradlew test --tests "com.lifttrack.api.SomeTest"

# Single test method
./gradlew test --tests "com.lifttrack.api.SomeTest.methodName"

# Pattern matching
./gradlew test --tests "*SomeTest"
```

### Docker (from monorepo root `/liftTrack/`)

```bash
docker compose -f docker-compose.db.yml up -d   # PostgreSQL only (local dev)
docker compose up -d                              # full stack (db + api + web)
```

### CI

GitHub Actions runs `gradle build` and `gradle test` on pushes/PRs to `main` touching `apps/api/**`. No lint step. No DB service container in CI — tests requiring a database will fail.

## Package Structure

```
com.lifttrack.api/
  LifttrackApiApplication.java

  domain/
    models/          # Java records — pure domain models (no framework deps)
    ports/           # Repository interfaces (domain contracts)

  infrastructure/
    persistence/
      entities/      # JPA entity classes (@Entity, Lombok)
      repositories/  # Spring Data JPA interfaces (JpaXxxRepository)
      mappers/       # Static utility classes: toDomain() / toEntity()
      adapters/      # Port implementations (@Component, @Transactional)
```

Every domain concept has exactly one model, port, entity, JPA repository, mapper, and adapter.

## Naming Conventions

| Layer | Pattern | Example |
|---|---|---|
| Domain model | `{Name}` (record) | `User`, `WorkoutSession` |
| Domain port | `{Name}Repository` (interface) | `UserRepository` |
| JPA entity | `{Name}Entity` | `UserEntity` |
| JPA repository | `Jpa{Name}Repository` | `JpaUserRepository` |
| Mapper | `{Name}Mapper` | `UserMapper` |
| Adapter | `{Name}RepositoryAdapter` | `UserRepositoryAdapter` |

- **Classes:** PascalCase. **Methods/fields:** camelCase. **Packages:** all lowercase.
- Adapter fields for JPA repos: `private final JpaXxxRepository jpaXxxRepository`.
- Spring Data query methods follow convention: `findByUuid`, `findByUserUuid`, `existsByEmail`.

## Import Ordering

Three groups separated by blank lines, in this order:

1. Project imports (`com.lifttrack.api.*`)
2. Third-party imports (`jakarta.*`, `lombok.*`, `org.springframework.*`)
3. Java standard library (`java.*`)

Never use wildcard imports. Each class imported individually.

## Domain Models (Records)

- Immutable Java `record` types. No framework annotations.
- Each has a `create(...)` instance method (not static) that validates inputs and passes `null` for auto-generated fields (`uuid`, `createdAt`, `updatedAt`).
- Validation uses sequential `if` checks throwing `IllegalArgumentException`:

```java
public record Exercise(UUID uuid, String name, ...) {
    public Exercise create(String name, ...) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        return new Exercise(null, name, ...);
    }
}
```

## JPA Entities

- Lombok annotations in order: `@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`.
- Primary key: `Long id` with `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`.
- External ID: `UUID uuid` with `@Column(nullable = false, unique = true, updatable = false)`.
- All relationships: `@ManyToOne(fetch = FetchType.LAZY)` — never eager, never `@OneToMany`.
- Column mapping: `@Column(name = "snake_case")`. Booleans use `is_` prefix in DB.

## Mappers

- `public final class` with `private` constructor (utility class pattern).
- Two static methods: `toDomain(XxxEntity)` and `toEntity(Xxx domain, ...relatedEntities)`.
- Related entities passed as extra parameters to `toEntity()`, resolved by the adapter.
- Null-safe for optional relationships: `entity.getUser() != null ? entity.getUser().getUuid() : null`.

## Adapters

- Annotated with `@Component` (not `@Repository` or `@Service`).
- Constructor injection via `@RequiredArgsConstructor`. All deps are `private final`.
- `@Transactional(readOnly = true)` on reads; `@Transactional` on writes.
- Entity lookups use `.orElseThrow(() -> new IllegalArgumentException("Type not found: " + uuid))`.
- Mapper results assigned with `var`: `var entity = XxxMapper.toEntity(...)`.

## Type Usage

| Use case | Type |
|---|---|
| Identifiers | `UUID` (never `String`) |
| Timestamps | `java.time.Instant` |
| Date-only fields | `java.time.LocalDate` |
| Weights / money | `java.math.BigDecimal` |
| Required integers | `int` (primitive) |
| Nullable integers | `Integer` (boxed) |
| Required booleans | `boolean` (primitive) |

- `var` for mapper return values. Explicit types for looked-up entities and when type isn't obvious.
- `Optional` only as return type on `findBy*` methods. Never as field or parameter type.
- Consume via `.map(Mapper::toDomain)` or `.orElseThrow(...)`.
- Prefer `.toList()` over `.collect(Collectors.toList())`.
- Use text blocks (`"""..."""`) for multi-line JPQL queries.

## Error Handling

- Use `IllegalArgumentException` for validation and not-found errors. No custom exception classes yet.
- No try-catch blocks — exceptions propagate upward.
- No `@ControllerAdvice` or global exception handler yet (controllers not implemented).

## Dependency Injection

- Always constructor injection via Lombok `@RequiredArgsConstructor`.
- Never use `@Autowired`.
- Domain port interfaces have no Spring annotations — pure Java interfaces.

## Code Style

- **No linting or formatting tools configured** (no Checkstyle, Spotless, etc.).
- **Minimal comments.** Code should be self-documenting. Only add comments when logic is non-obvious (e.g., custom JPQL queries). Build file comments may be in Spanish.
- **No logging** is used in the current codebase.
- **JPA config:** `open-in-view: false`, `ddl-auto: validate` (schema managed by Flyway).

## Java 21 Features in Use

- `record` types for domain models
- Text blocks for JPQL queries
- `.toList()` stream terminal operation
- Local variable type inference (`var`) where type is obvious

## Key Configuration

- **App config:** `src/main/resources/application.yml`
- **DB migration:** `src/main/resources/db/migration/V1__init.sql`
- **API docs:** `/swagger-ui` (Swagger UI), `/api-docs` (OpenAPI spec)
- **Spring profiles:** none defined yet; OAuth2 config is commented out
