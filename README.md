# Kotlin Spring Boot Service with PostgreSQL

A web service skeleton built with Kotlin, Spring Boot, and PostgreSQL.

## Prerequisites

- Java 21 (LTS)
- Docker and Docker Compose (recommend OrbStack on macOS!)
- Gradle 9 (use the Gradle wrapper)
- SDKMAN (optional, for Java version management)

## Project Structure

```
kotlin-spring-service/
├── src/
│   ├── main/
│   │   ├── kotlin/com/example/demo/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── service/         # Business logic
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── model/           # Entity models
│   │   │   └── DemoApplication.kt
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/    # Flyway migrations
│   └── test/
├── docker-compose.yml
├── build.gradle.kts
├── gradle.properties
└── .sdkmanrc
```

## Getting Started

### 1. Set Up Java 21 (if using SDKMAN)

```bash
# Install Java 21
sdk install java 21.0.5-tem

# Set as default
sdk default java 21.0.5-tem

# Or use the .sdkmanrc file to auto-activate
sdk env
```

### 2. Start PostgreSQL Database

```bash
docker-compose up -d
```

This will start a PostgreSQL 16 container on port 5432 with:
- Database: `demodb`
- Username: `postgres`
- Password: `postgres`

### 3. Run the Application

**Production mode (with reference data only):**
```bash
./gradlew bootRun
```

**Development mode (with test data):**
```bash
./gradlew rundev
```

Or use the longer form:
```bash
./gradlew bootRun --args='--spring.profiles.active=dev'
```

**Build and run the JAR:**
```bash
./gradlew build
java -jar build/libs/demo-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:8080`

## API Endpoints

### User Management

- **GET** `/api/users` - Get all users
- **GET** `/api/users/{id}` - Get user by ID
- **POST** `/api/users` - Create a new user
- **PUT** `/api/users/{id}` - Update user
- **DELETE** `/api/users/{id}` - Delete user

### Example Requests

Create a user:
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name": "John Doe", "email": "john@example.com"}'
```

Get all users:
```bash
curl http://localhost:8080/api/users
```

## Development

### Database Management

View database logs:
```bash
docker-compose logs -f postgres
```

Connect to PostgreSQL:
```bash
docker exec -it demo-postgres psql -U postgres -d demodb
```

Stop the database:
```bash
docker-compose down
```

Stop and remove volumes:
```bash
docker-compose down -v
```

### Configuration

Database configuration can be found in `src/main/resources/application.yml`.

The project includes a `dev` profile that activates test data seeding. Configuration is split between:
- Base configuration (default)
- Dev profile configuration (activated with `--spring.profiles.active=dev`)

## Database Migrations

This project uses **Flyway** for database migrations:

- Migrations are located in `src/main/resources/db/migration/`
- `V1__create_users_table.sql` - Creates the users table
- `V2__seed_initial_data.sql` - Seeds production reference data (3 users)

Migrations run automatically on application startup.

## Data Seeding

The project includes two data seeding mechanisms:

### 1. Production Reference Data (Flyway)
- Seeds 3 reference users (Admin, System, Support)
- Runs in ALL environments
- Located in `V2__seed_initial_data.sql`

### 2. Development Test Data (Spring CommandLineRunner)
- Seeds 10 test users
- Only runs with `dev` profile active
- Located in `src/main/kotlin/com/example/demo/config/DevDataSeeder.kt`
- Smart duplicate detection using `john.doe@test.com` as marker

Run with dev profile to get all 13 users (3 reference + 10 test).

## Testing

Run tests:
```bash
./gradlew test
```

Run tests with detailed output:
```bash
./gradlew test --info
```

## Technologies Used

- **Kotlin**
- **Spring Boot**
- **Spring Data JPA**
- **SpringDoc OpenAPI**
- **PostgreSQL**
- **Flyway**
- **Gradle**
- **Java**
- **Docker**
