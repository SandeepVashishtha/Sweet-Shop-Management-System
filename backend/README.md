# Sweet Shop Management System - Backend

A robust RESTful API backend for managing a sweet shop, built with Spring Boot, PostgreSQL, and JWT authentication. This project demonstrates Test-Driven Development (TDD) practices with comprehensive test coverage.

## 🚀 Features

- **User Authentication**: JWT-based authentication with secure password hashing
- **Role-Based Authorization**: User and Admin roles with different permissions
- **Sweet Management**: Full CRUD operations for sweets inventory
- **Inventory Control**: Purchase and restock operations with stock validation
- **Advanced Search**: Search sweets by name, category, and price range
- **Input Validation**: Comprehensive validation using Bean Validation
- **Exception Handling**: Global exception handler with meaningful error messages
- **Test Coverage**: Unit and integration tests following TDD principles

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL Database (Aiven hosted - configured)
- Git

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security with JWT (jjwt 0.12.3)
- **Database**: MySQL (Aiven Cloud) with Spring Data JPA
- **Testing**: JUnit 5, Mockito, Spring Boot Test
- **Build Tool**: Maven
- **Others**: Lombok, Bean Validation

## ⚙️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <your-repository-url>
cd Sweet-Shop-Management-System/backend
```

### 2. Database Configuration

**✅ Aiven MySQL Database Already Configured!**

Your application is pre-configured to use Aiven MySQL cloud database. No local database setup needed!

If you want to use a local MySQL instead, see `AIVEN_SETUP.md` for configuration options.

### 4. Build the Project

```bash
mvn clean install
```

### 5. Run Tests

```bash
mvn test
### 3. Build the Project

### 6. Run the Application

```bash
mvn spring-boot:run
### 4. Run Tests

The API will start at `http://localhost:8080`

## 📚 API Documentation

### 5. Run the Applicationts

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "securePassword123"
}
```

**Response**: `201 CREATED`
```json
{
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "role": "USER",
  "token": null,
  "type": "Bearer"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john_doe",
  "password": "securePassword123"
}
```

**Response**: `200 OK`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "type": "Bearer",
  "id": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "role": "USER"
}
```

### Sweet Management Endpoints (Protected)

All sweet endpoints require authentication. Include the JWT token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Create Sweet
```http
POST /api/sweets
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Chocolate Bar",
  "category": "Chocolate",
  "price": 2.50,
  "quantity": 100,
  "description": "Delicious milk chocolate"
}
```

**Response**: `201 CREATED`

#### Get All Sweets
```http
GET /api/sweets
Authorization: Bearer <token>
```

**Response**: `200 OK`
```json
[
  {
    "id": 1,
    "name": "Chocolate Bar",
    "category": "Chocolate",
    "price": 2.50,
    "quantity": 100,
    "description": "Delicious milk chocolate",
    "createdAt": "2025-12-14T10:30:00",
    "updatedAt": "2025-12-14T10:30:00"
  }
]
```

#### Get Sweet by ID
```http
GET /api/sweets/{id}
Authorization: Bearer <token>
```

#### Search Sweets
```http
GET /api/sweets/search?name=chocolate&category=Chocolate&minPrice=1.00&maxPrice=5.00
Authorization: Bearer <token>
```

All query parameters are optional.

#### Update Sweet
```http
PUT /api/sweets/{id}
Authorization: Bearer <token>
Content-Type: application/json

{
  "name": "Premium Chocolate Bar",
  "category": "Chocolate",
  "price": 3.50,
  "quantity": 150,
  "description": "Premium quality chocolate"
}
```

**Response**: `200 OK`

#### Delete Sweet (Admin Only)
```http
DELETE /api/sweets/{id}
Authorization: Bearer <token>
```

**Response**: `204 NO CONTENT`

### Inventory Endpoints (Protected)

#### Purchase Sweet
```http
POST /api/sweets/{id}/purchase
Authorization: Bearer <token>
Content-Type: application/json

{
  "quantity": 5
}
```

**Response**: `200 OK` - Returns updated sweet with decreased quantity

#### Restock Sweet (Admin Only)
```http
POST /api/sweets/{id}/restock
Authorization: Bearer <token>
Content-Type: application/json

{
  "quantity": 50
}
```

**Response**: `200 OK` - Returns updated sweet with increased quantity

## 🧪 Testing

The project follows Test-Driven Development (TDD) practices with comprehensive test coverage.

### Run All Tests
```bash
mvn test
```

### Run Tests with Coverage Report
```bash
mvn test jacoco:report
```

Coverage report will be generated at: `target/site/jacoco/index.html`

### Test Structure

- **Unit Tests**: 
  - `AuthServiceTest.java` - Authentication service logic
  - `SweetServiceTest.java` - Sweet management service logic

- **Integration Tests**:
  - `AuthControllerIntegrationTest.java` - End-to-end authentication tests

### Example Test Results

```
[INFO] Tests run: 25, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
```

## 🔒 Security

- Passwords are hashed using BCrypt
- JWT tokens expire after 24 hours (configurable)
- Role-based access control (USER, ADMIN)
- CORS configured for frontend integration
- Stateless session management

### Creating an Admin User

**Option 1: Use Dev Profile** (Recommended)
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```
This automatically creates admin user: `admin` / `admin123`

**Option 2: Manual Database Update**
```sql
UPDATE users SET role = 'ADMIN' WHERE username = 'your_username';
```
You can run this in Aiven Console > Query Editor or MySQL Workbench.

## 🤖 My AI Usage

### AI Tools Used

I extensively used **GitHub Copilot** throughout the development of this project, following the Test-Driven Development approach.

### How I Used AI

1. **Project Structure & Boilerplate**:
   - Used Copilot to generate initial Maven POM configuration with all necessary Spring Boot dependencies
   - Generated boilerplate code for entity classes (User, Sweet) with JPA annotations
   - Created DTO classes with validation annotations

2. **Test-Driven Development**:
   - **RED Phase**: Asked Copilot to help structure test cases for services and controllers
   - Example: "Generate unit tests for AuthService with cases for successful registration, duplicate username, and duplicate email"
   - Copilot suggested test method names and basic structure, which I then refined
   - Used AI to generate mock objects and test data setup in `@BeforeEach` methods

3. **Implementation (GREEN Phase)**:
   - After writing tests, used Copilot's suggestions to implement service methods
   - Example: While writing `AuthService.registerUser()`, Copilot suggested the password encoding and entity creation logic
   - Copilot helped implement repository query methods in `SweetRepository`

4. **Security Configuration**:
   - Used Copilot to generate JWT token provider boilerplate
   - AI suggested the security filter chain configuration structure
   - Manually reviewed and adjusted security rules for role-based authorization

5. **Exception Handling**:
   - Copilot generated the global exception handler structure
   - AI suggested different exception types and HTTP status codes
   - I refined the error response format for consistency

6. **Code Refinement (REFACTOR Phase)**:
   - Used Copilot to suggest better naming conventions
   - AI helped identify code duplication in controller response mapping
   - Suggested using stream operations for list transformations

### What AI Did Well

- **Speed**: Significantly reduced time for boilerplate code generation
- **Pattern Recognition**: Excellent at following established patterns (e.g., once it saw my test structure, it maintained consistency)
- **Documentation**: Helpful in generating JavaDoc comments and method descriptions
- **Standard Practices**: Suggested industry-standard approaches for Spring Boot configuration

### What I Did Manually

- **Business Logic**: All core business rules (stock validation, purchase logic) were written by me
- **Test Cases Design**: I designed the test scenarios based on requirements; AI only helped with syntax
- **Database Schema**: Designed entity relationships and column specifications
- **Security Rules**: Defined which endpoints require authentication vs. admin-only access
- **Error Messages**: Wrote user-friendly error messages for exceptions
- **Code Review**: Thoroughly reviewed all AI-generated code for correctness and security

### Reflection on AI Impact

**Positive Impact**:
- Reduced development time by approximately 30-40% for repetitive code
- Helped maintain consistency across the codebase
- Useful for learning new Spring Boot features and best practices
- Excellent for generating test data and mock setups

**Challenges**:
- Required careful review to ensure AI-generated code matched project requirements
- Sometimes suggested outdated patterns or dependencies
- Needed manual intervention for complex business logic
- Had to verify security implementations independently

**Key Learning**: AI is a powerful accelerator but not a replacement for understanding. I found the most value when I had a clear plan (especially with TDD), and AI helped execute it faster. Critical thinking and code review remain essential skills.

## 🏗️ Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/sweetshop/
│   │   │   ├── config/           # Security and app configuration
│   │   │   ├── controller/       # REST API controllers
│   │   │   ├── dto/              # Data Transfer Objects
│   │   │   ├── entity/           # JPA entities
│   │   │   ├── exception/        # Custom exceptions and handlers
│   │   │   ├── repository/       # Spring Data repositories
│   │   │   ├── security/         # JWT and security classes
│   │   │   ├── service/          # Business logic
│   │   │   └── SweetShopApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       ├── java/com/sweetshop/
│       │   ├── controller/       # Integration tests
│       │   └── service/          # Unit tests
│       └── resources/
│           └── application-test.properties
├── pom.xml
└── README.md
```

## 🐛 Error Handling

The API uses a global exception handler that returns consistent error responses:

```json
{
  "timestamp": "2025-12-14T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Sweet not found with id: 1",
  "path": "/api/sweets/1"
}
```

### Common HTTP Status Codes

- `200 OK` - Successful GET, PUT requests
- `201 CREATED` - Successful POST (create) requests
- `204 NO CONTENT` - Successful DELETE requests
- `400 BAD REQUEST` - Validation errors, insufficient stock
- `401 UNAUTHORIZED` - Missing or invalid JWT token
- `403 FORBIDDEN` - Insufficient permissions
- `404 NOT FOUND` - Resource not found
- `409 CONFLICT` - Duplicate username/email
- `500 INTERNAL SERVER ERROR` - Server errors

## 🚀 Deployment

### Build Production JAR

```bash
mvn clean package -DskipTests
```

The JAR file will be created at: `target/sweet-shop-backend-1.0.0.jar`

### Run Production Build

```bash
java -jar target/sweet-shop-backend-1.0.0.jar
```

### Environment Variables for Production

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://your-db-host:5432/sweet_shop_db
export SPRING_DATASOURCE_USERNAME=your_username
export SPRING_DATASOURCE_PASSWORD=your_password
export JWT_SECRET=your-production-secret-key-min-256-bits
export JWT_EXPIRATION=86400000
```

## 📝 Git Commit Convention

This project follows conventional commits with AI co-authorship transparency:

```bash
git commit -m "feat: Implement user registration endpoint

Used GitHub Copilot to generate initial boilerplate for the controller 
and service, then manually added validation logic and error handling.

Co-authored-by: GitHub Copilot <copilot@github.com>"
```

## 📄 License

This project is developed as part of a coding assessment.

## 👤 Author

[Your Name]

## 🙏 Acknowledgments

- Spring Boot team for excellent documentation
- GitHub Copilot for development assistance
- PostgreSQL community

---

**Note**: This is a portfolio project demonstrating TDD practices, Spring Boot development, and responsible AI tool usage.
