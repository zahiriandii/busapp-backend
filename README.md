# Bus Ticket Reservation System – Backend

## Description

This project represents the backend of a **Bus Ticket Reservation System**, developed as part of a **university diploma thesis**. The backend is responsible for handling business logic, data persistence, security, payments, and communication with the mobile application and the admin panel.

It provides RESTful APIs for managing trips, bookings, passengers, seat reservations, and administrative operations.

---

## Technologies Used

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* Spring Security with JWT authentication
* PostgreSQL
* Stripe API (online payments)
* Java Mail Sender (email notifications)
* Maven

---

## Configuration

Before running the application, configure the database and other required services in the `application.yml` file.

Example database configuration:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/busapp-db
    username: postgres
    password: your_password
```

Stripe secret keys and email credentials should be configured using environment variables or placeholders for local testing.

---

## Running the Application

1. Clone the backend repository
2. Create a PostgreSQL database
3. Update the `application.yml` configuration file
4. Run the application using Maven:

```bash
mvn spring-boot:run
```

After startup, the backend will be available at:

```
http://localhost:8080
```
