# Quick Start Guide - Sweet Shop Backend

This guide will help you get the backend API up and running in minutes.

## Prerequisites Check

Before starting, ensure you have:
- [ ] Java 17 or higher installed
- [ ] Maven 3.6+ installed
- [ ] PostgreSQL 12+ installed and running
- [ ] Git installed

## 5-Minute Setup

### Step 1: Clone and Navigate
```bash
git clone <your-repository-url>
cd Sweet-Shop-Management-System/backend
```

### Step 2: Build and Test (Database Already Configured!)
```bash
mvn clean install
mvn test
```

### Step 5: Run the Application
```bash
mvn spring-boot:run
```

✅ **Done!** API is now running at http://localhost:8080

## First API Call

### 1. Register a User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
### Step 3: Run the Application

### 2. Login and Get Token
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "password": "password123"
  }'
```

**Save the token from the response!**

### 3. Create a Sweet
```bash
curl -X POST http://localhost:8080/api/sweets \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "name": "Chocolate Bar",
    "category": "Chocolate",
    "price": 2.50,
    "quantity": 100,
    "description": "Delicious chocolate"
  }'
```

### 4. Get All Sweets
```bash
curl -X GET http://localhost:8080/api/sweets \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Using Postman

1. Import the collection: `Sweet-Shop-API.postman_collection.json`
2. Run "Register User" request
3. Run "Login" request (token is auto-saved)
4. Run any other request (token is auto-included)

## Development Mode with Sample Data

To start with pre-populated data:

1. Set active profile to `dev` in `application.properties`:
```properties
spring.profiles.active=dev
```

2. Run the application:
```bash
mvn spring-boot:run
```

**Default credentials**:
- Admin: `admin` / `admin123`
- User: `user` / `user123`

**Sample sweets** are automatically created.

## Common Issues

### Database Connection Failed
```
Error: Communications link failure
```
**Solution**: 
- Check your internet connection (Aiven MySQL is cloud-hosted)
- Verify Aiven service is running in console.aiven.io
- See `AIVEN_SETUP.md` for detailed troubleshooting

### Port 8080 Already in Use
```
Error: Port 8080 is already in use
```
**Solution**: Change port in `application.properties`:
```properties
server.port=8081
```

### Tests Failing
```
Error: Tests fail with database connection error
```
### Tests Failing
```
Error: Tests fail with database connection error
```
**Solution**: Tests use H2 in-memory database (no Aiven connection needed). Check if H2 dependency is in `pom.xml`.
- [ ] Test all endpoints with Postman
- [ ] Review test coverage: `mvn test jacoco:report`
- [ ] Explore the code structure
- [ ] Try implementing a new feature with TDD

## Quick Commands Reference

```bash
# Build
mvn clean install

# Run tests
mvn test

# Run with coverage
mvn test jacoco:report

# Start application
mvn spring-boot:run

# Package for production
mvn clean package -DskipTests

# Run packaged JAR
java -jar target/sweet-shop-backend-1.0.0.jar
```

## Need Help?

- Check the [full README](README.md)
- Review [API Documentation](README.md#-api-documentation)
- Check test examples in `src/test/java/`

---

**Happy Coding! 🍬**
