¿Qué hace? (en una frase)
Administra productos, clientes, ventas y stock de forma que todo esté conectado y actualizado en tiempo real.
Los 4 pilares
1. 📦 Productos
Podés cargar lo que vendés con nombre, precio, código, y stock. Cuando alguien compra, el stock se descuenta solo. Cuando te devuelven algo, el stock se recompone solo. Nunca más vas a vender algo que no tenés.
2. 👥 Clientes
Podés guardar quiénes te compran, su historial, sus datos de contacto. Cuando un cliente llame, en 2 segundos sabés qué compró, cuándo, y por cuánto.
3. 🧾 Ventas
Podés registrar cada venta con todos sus productos, el total, y el método de pago. Después podés ver cuánto vendiste hoy, este mes, o filtrar por fechas. Si una venta se cancela, el stock vuelve solo — no tenés que acordarte de hacerlo a mano.
4. 📊 Dashboard (el panel de control)
Apenas abrís el sistema, ves una sola pantalla con:
- Total de productos que tenés
- Cuántos clientes registraste
- Ventas de hoy y cuánta plata entró
- Qué productos tienen stock bajo (para que no te agarre de sorpresa)
- Últimas ventas (los movimientos más recientes)

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
