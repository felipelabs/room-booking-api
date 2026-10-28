# Room Booking API

A Spring Boot REST API for reserving shared meeting rooms. This portfolio project demonstrates a complete backend service: validation, persistence, schema migrations, conflict handling, pagination, containerisation, and CI packaging.

## Features

- Create, list, retrieve, and cancel room bookings
- Reject overlapping bookings with HTTP 409 Conflict
- Serialize booking attempts for the same room with a PostgreSQL row lock
- Use half-open intervals, so one booking can start as another ends
- Validate request fields and return RFC 9457 ProblemDetail errors
- Manage schema changes with Flyway; Hibernate runs in validate mode
- Expose health endpoints for container orchestration

## Stack

Java 17 · Spring Boot 3.5 · Spring Web · Spring Data JPA · Bean Validation · PostgreSQL 16 · Flyway · Docker Compose · GitHub Actions

## Run locally

Requires Docker with Compose.

~~~sh
docker compose up --build
~~~

The API is available at localhost port 8080. PostgreSQL data is stored in the booking-data volume.

~~~sh
curl http://localhost:8080/api/v1/rooms
~~~

Use a room ID returned by the previous request to create a booking:

~~~sh
curl -X POST http://localhost:8080/api/v1/bookings -H 'Content-Type: application/json' -d '{"roomId":"10000000-0000-0000-0000-000000000001","requesterName":"Alex Murphy","requesterEmail":"alex@example.ie","startsAt":"2027-02-10T10:00:00Z","endsAt":"2027-02-10T11:00:00Z"}'
~~~

## API

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/v1/rooms | List active rooms |
| POST | /api/v1/bookings | Create a booking |
| GET | /api/v1/bookings?page=0&size=20 | List bookings |
| GET | /api/v1/bookings/{id} | Retrieve one booking |
| POST | /api/v1/bookings/{id}/cancellation | Cancel a booking |
| GET | /actuator/health | Health status |

See requests/room-booking.http for runnable examples.

## Concurrency and data integrity

The service locks the selected room row in the booking transaction before checking for an overlap and inserting. Competing requests for the same room wait on one another, so only one overlapping booking is confirmed. Different rooms can still be booked concurrently. PostgreSQL also enforces positive durations and valid room references.

The example runs as a single API instance. A high-volume or multi-region scheduling system should revisit the locking and time-zone policy against its production database and business rules.

## Configuration

| Variable | Default | Purpose |
|---|---|---|
| DB_URL | jdbc:postgresql://localhost:5432/bookings | JDBC connection URL |
| DB_USERNAME | bookings | Database user |
| DB_PASSWORD | bookings | Database password |
| SERVER_PORT | 8080 | HTTP port |

Defaults are intended for local development. Use managed credentials outside local use.
