# ERP Minimalista - Backend

Backend desarrollado con **Java 17+** y **Spring Boot**.

## Arquitectura
Implementamos **Arquitectura Hexagonal (Ports & Adapters)** para desacoplar la lógica de negocio del framework y la persistencia:
- `domain/`: Lógica pura de negocio (POJOs, reglas, servicios).
- `application/`: Casos de uso.
- `infrastructure/`: Implementaciones JPA y REST Controllers.

## Tecnologías
- **Framework:** Spring Boot 3+
- **Persistencia:** PostgreSQL + Spring Data JPA
- **Migraciones:** Flyway
- **Gestión:** Gradle
