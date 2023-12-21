# Flight Search API - README

---


## Description
This project involves the development of a backend API for a flight search application. The API is responsible for managing and retrieving information related to flights and airports.

## Prerequisites
Before running the project, make sure to configure the following settings in the `application.properties` file.

### Database Configuration
Set the database connection properties:
``` Properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
```

### Security Configuration
Set the Security Configuration true if you want to enable Spring Security:
``` Properties
security.enable=false
```
## Technologies Used
- Java (Version 17)
- Spring Boot
- Spring Data JPA
- Spring Security
- JWT (JSON Web Token)
- MySQL
- Lombok
- ModelMapper
- Swagger
- Maven
- 
### Expectations
#### Data Modeling
- Design and model information to be stored in the database.
- Utilized either a relational (SQL) or NoSQL database.
- Information to be stored:
    - Flights
        - ID
        - Departure airport
        - Arrival airport
        - Departure date/time
        - Return date/time
        - Price
    - Airports
        - ID
        - City
    - User
        - ID
        - Username
        - Email
        - password
        - Created Date
    - Refresh Token
        - ID
        - User
        - Token
        - Expiry Date

#### CRUD Structure
- Implemented CRUD (Create, Read, Update, Delete) operations for flights and airports.

#### Search API
- Created an API endpoint that lists flights based on departure/arrival locations, departure date, and return date.
- Returned single-flight information for one-way flights and two-flight information for round trips.

#### RESTful Service
- Exposed services using REST architecture, facilitating easy data exchange and system integration.
- Utilized Java with Spring/Spring Boot.

#### Authentication
- Implemented an authentication structure to verify and authorize users.
- Chosen an appropriate authentication architecture.

#### Scheduled Background Jobs
- Developed a scheduled job that daily fetches flight information from a third-party API and stores it in the database.
- Used mock API requests to simulate fetching data from a third-party source.

#### Version Control
- Utilized Git for version control.
- Uploaded the project to GitHub.

#### Documentation
- Created API documentation using Swagger.

## Project Structure
```plaintext
flightsearch
|-- config
|   |-- ModelMapperConfig
|   |-- SecurityConfig
|   |-- SwaggerConfig
|-- controller
|   |-- AirportController
|   |-- AuthController
|   |-- FlightController
|   |-- MockController
|   |-- UserController
|-- dto
|   |-- request
|   |   |-- LoginRequest
|   |   |-- RefreshRequest
|   |   |-- RegisterRequest
|   |   |-- SearchRequest
|   |-- response
|   |   |-- AuthResponse
|   |   |-- AirportDTO
|   |   |-- FlightDTO
|   |   |-- UserDTO
|-- entity
|   |-- Airport
|   |-- Flight
|   |-- RefreshToken
|   |-- User
|-- helper
|   |-- FlightSearchHelper
|-- repository
|   |-- AirportRepository
|   |-- FlightRepository
|   |-- RefreshTokenRepository
|   |-- UserRepository
|-- security
|   |-- JwtAuthenticationEntryPoint
|   |-- JwtAuthenticationFilter
|   |-- JwtTokenProvider
|   |-- JwtUserDetails
|-- service
|   |-- impl
|   |   |-- AirportServiceImpl
|   |   |-- AuthServiceImpl
|   |   |-- FlightServiceImpl
|   |   |-- RefreshTokenServiceImpl
|   |   |-- UserDetailsServiceImpl
|   |   |-- UserServiceImpl
|   |-- AirportService
|   |-- AuthService
|   |-- FlightService
|   |-- RefreshTokenService
|   |-- UserService
|-- FlightSearchApplication

