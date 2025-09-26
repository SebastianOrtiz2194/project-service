# Project Service

A REST API microservice built with **Java 21**, **Spring Boot 3.5.6**, **Oracle XE**, and **AWS S3**.

## Features
- Manage `Project` entities with CRUD operations.
- Upload attachments for projects, stored in **AWS S3**.
- Exposes API docs via **Swagger UI** (springdoc).

## Tech Stack
- Java 21
- Spring Boot 3.5.6
- Oracle XE (Dockerized)
- AWS S3 SDK v2
- Maven
- Docker & Docker Compose

## Project Structure
```
src/main/java/com/example/project
 ├── entity         # JPA entities (Project, Attachment)
 ├── repository     # Spring Data repositories
 ├── service        # Business logic + S3 upload service
 ├── web            # REST controllers
 └── ProjectServiceApplication.java
```

## Running Locally

### 1. Prerequisites
- Java 21
- Maven 3.9+
- Docker + Docker Compose
- AWS account with S3 bucket
- Configure your AWS credentials (`AWS_ACCESS_KEY_ID`, `AWS_SECRET_ACCESS_KEY`)

### 2. Build and Run with Docker Compose
```bash
docker-compose up --build
```

This starts:
- Oracle XE (listening on port `1521`)
- Project Service (listening on port `8080`)

### 3. Access the API
- Swagger UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- Health check:
```bash
curl http://localhost:8080/api/projects
```

### 4. Configure AWS S3
In `application.properties` or environment variables:
```
AWS_REGION=ap-northeast-1
AWS_S3_BUCKET=your-bucket-name
AWS_ACCESS_KEY_ID=your-access-key
AWS_SECRET_ACCESS_KEY=your-secret-key
```

### 5. Postman Collection
A Postman collection file is included:  
`ProjectService.postman_collection.json`

Import into Postman to test:
- Create Project (`POST /api/projects`)
- List Projects (`GET /api/projects`)
- Upload File (`POST /api/projects/{id}/upload`)

### 6. Build without Docker
```bash
mvn clean package -DskipTests
java -jar target/project-service-0.0.1-SNAPSHOT.jar
```

## Notes
- Oracle XE default connection: `jdbc:oracle:thin:@//localhost:1521/XEPDB1`  
  user: `system`  
  password: `oracle`  
- Database schema auto-created (`spring.jpa.hibernate.ddl-auto=update`).

## License
MIT
