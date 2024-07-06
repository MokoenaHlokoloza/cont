# MTN Contract Repayment System

## Description
The MTN Contract Repayment System is a comprehensive web application designed to manage contract repayments effectively. This solution combines a robust Java Spring Boot backend with a dynamic Angular frontend. The system is engineered to serve both API endpoints and static frontend files from a single Spring Boot application, simplifying deployment and operations.

## Prerequisites
- Java 17
- Maven
- Node.js (v22.3.0)
- npm (v10.8.1)

## Technologies
- **Spring Boot 3.2.7**: Framework for creating stand-alone, production-grade Spring based Applications.
- **Java JDK 17**: The development environment for building applications and components using the Java programming language.
- **Spring Security**: Provides authentication and authorization mechanisms.
- **Spring Data JPA**: Facilitates database interactions.
- **H2 Database**: In-memory database used for development and testing purposes.
- **JWT (JSON Web Tokens)**: Ensures secure communication between the client and the server.
- **Angular**: Frontend framework for building dynamic web interfaces.
- **Swagger**: Tool for documenting APIs exposed by a Spring Boot application.

## Installation and Setup
1. **Clone the repository**:

2. **Build the project**:
   Navigate to the root directory of the project and run:
```bash
##B Build the app
mvn clean install

## Run the Spring Boot application:
mvn spring-boot:run

## Generate JaCoCo report
mvn test jacoco:report
```

## Security Features
The application utilizes Spring Security with JWT for securing REST API endpoints. This setup ensures that all transactions are authenticated and authorized, providing a secure environment for operation.

## API Documentation
Swagger is integrated to automatically generate live documentation for all exposed APIs. This can be accessed at http://localhost:8080/swagger-ui.html once the application is running. It provides a detailed view of all REST endpoints, including the expected parameters and the response models.

## Monitoring
Spring Boot Actuator is enabled, providing essential management and monitoring capabilities accessible at http://localhost:8080/actuator. It offers health, info, and metric data for the application.


