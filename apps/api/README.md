# LiftTrack API

REST API para una aplicacion de seguimiento de entrenamiento de fuerza. Permite a los usuarios gestionar rutinas, registrar sesiones de entrenamiento, y llevar un control de sus records personales.

## Stack

Java 21 | Spring Boot 4.0.3 | PostgreSQL 16 | Flyway | Spring Security + JWT

## Endpoints principales

| Recurso | Descripcion |
|---|---|
| `POST /api/v1/auth/register, /login, /refresh` | Registro, login y refresh de tokens JWT |
| `GET/POST/DELETE /api/v1/exercises` | Catalogo de ejercicios (globales y por usuario) |
| `GET /api/v1/muscle-groups` | Grupos musculares |
| `GET/POST/DELETE /api/v1/routines` | Rutinas de entrenamiento |
| `GET/POST/DELETE /api/v1/routines/{uuid}/days` | Dias dentro de una rutina |
| `GET/POST/DELETE /api/v1/routine-days/{uuid}/exercises` | Ejercicios asignados a un dia |
| `GET/POST/DELETE /api/v1/workout-sessions` | Sesiones de entrenamiento |
| `GET/POST/DELETE /api/v1/workout-sessions/{uuid}/exercises` | Ejercicios realizados en una sesion |
| `GET/POST/DELETE /api/v1/workout-session-exercises/{uuid}/sets` | Series de cada ejercicio |
| `GET/POST/DELETE /api/v1/personal-records` | Records personales |
| `GET /api/v1/exercises/{uuid}/last-set` | Ultima serie registrada de un ejercicio |

Documentacion interactiva disponible en `/swagger-ui` una vez levantado el servidor.

## Setup rapido

```bash
# 1. Levantar PostgreSQL
docker compose -f docker-compose.db.yml up -d

# 2. Arrancar la API
./gradlew bootRun
```

La API corre en `http://localhost:8080`. Las credenciales por defecto de la base de datos estan en `application.yml`.

## Autenticacion

Todos los endpoints (excepto `/api/v1/auth/**` y Swagger) requieren un token JWT en el header:

```
Authorization: Bearer <access_token>
```

El token de acceso expira en 15 minutos. Usa el endpoint `/refresh` con el refresh token (7 dias) para obtener uno nuevo.

## Arquitectura

El proyecto sigue arquitectura hexagonal (puertos y adaptadores):

```
domain/          -> Modelos (records) e interfaces (ports). Sin dependencias de framework.
application/     -> Casos de uso. Un @Component por operacion con un metodo execute().
infrastructure/  -> Implementaciones: JPA, controladores REST, seguridad JWT.
```

## Comandos utiles

```bash
./gradlew build        # compilar + tests
./gradlew test         # solo tests
./gradlew clean build  # rebuild completo
```
