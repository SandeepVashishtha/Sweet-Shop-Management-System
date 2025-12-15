# Sweet Shop Management System 🍬

A full-stack application for managing a sweet shop with user authentication, inventory management, and role-based authorization.

## 🎯 Project Overview

This project is a comprehensive Sweet Shop Management System built following Test-Driven Development (TDD) principles. It includes:

- **Backend API**: RESTful API built with Spring Boot and JWT authentication
- **Frontend**: Modern React SPA with responsive UI
- **Database**: MySQL hosted on Aiven Cloud
- **Security**: JWT-based authentication with role-based access control
- **Testing**: Comprehensive unit and integration tests

## 📁 Project Structure

```
Sweet-Shop-Management-System/
├── backend/                 # Spring Boot REST API
│   ├── src/
│   │   ├── main/java/      # Application source code
│   │   ├── test/java/      # Unit and integration tests
│   │   └── resources/      # Configuration files
│   ├── pom.xml             # Maven dependencies
│   ├── README.md           # Backend documentation
│   └── setup.ps1           # Windows setup script
├── frontend/               # React Frontend
│   ├── src/
│   │   ├── components/    # React components
│   │   ├── context/       # Context providers (Auth)
│   │   ├── services/      # API service layer
│   │   ├── App.jsx        # Main app component
│   │   └── main.jsx       # Entry point
│ **Java 17** or higher
- **Maven 3.6+**
- **Node.js 16+** and npm
- **Git**
- MySQL Database (Aiven hosted - already configured)

### Backend Setup

1. **Navigate to backend directory**:
   ```bash
   cd backend
   ```

2. **Run the setup script** (Windows):
   ```powershell
   .\setup.ps1
   ```

   Or **manually**:
   ```bash
   # Install dependencies
   mvn clean install

   # Run tests
   mvn test

   # Start the application
   mvn spring-boot:run
   ```

3. **Access the API**: http://localhost:8080

### Frontend Setup

1. **Navigate to frontend directory**:
   ```bash
   cd frontend
   ```

2. **Install dependencies**:
   ```bash
   npm install
   ```

3. **Start development server**:
   ```bash
   npm run dev
   ```

4. **Access the application**: http://localhost:5173

For detailed documentation:
- Backend: [backend/README.md](backend/README.md)
- Frontend: [frontend/README.md](front
   # Run tests
   mvn test

   # Start the application
   mvn spring-boot:run
   ```

3. **Access the API**: http://localhost:8080

For detailed backend documentation, see [backend/README.md](backend/README.md)

## 📚 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login and get JWT token

### Sweets Management (Protected)
- `POST /api/sweets` - Create a new sweet
- `GET /api/sweets` - Get all sweets
- `GET /api/sweets/{id}` - Get sweet by ID
- `GET /api/sweets/search` - Search sweets by criteria
- `PUT /api/sweets/{id}` - Update sweet details
- `DELETE /api/sweets/{id}` - Delete sweet (Admin only)

### Inventory (Protected)
- `POST /api/sweets/{id}/purchase` - Purchase a sweet
- `POST /api/sweets/{id}/restock` - Restock a sweet (Admin only)

## 🧪 Testing

The project follows Test-Driven Devel (jjwt 0.12.3)
- **Database**: MySQL 8.1.0 (Aiven Cloud)
- **ORM**: Spring Data JPA / Hibernate 6.3.1
- **Testing**: JUnit 5, Mockito, H2 (test database)
- **Build Tool**: Maven

### Frontend
- **Library**: React 18
- **Build Tool**: Vite 5
- **Routing**: React Router v6
- **HTTP Client**: Axios
- **Styling**: CSS3 (custom styles)
- **Authentication**: JWT token managemente for testing

## 🔒 Authentication

The API uses JWT (JSON Web Token) for authentication:

1. Register or login to get a JWT token
2. Include the token in subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

### Default Test Credentials

When running with dev profile:
- **Admin**: username=`admin`, password=`admin123`
- **User**: username=`user`, password=`user123`

## 🛠️ Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.0
- **Security**: Spring Security + JWT
- **Database**: MySQL (Aiven Cloud)
- **ORM**: Spring Data JPA / Hibernate
- **Testing**: JUnit 5, Mockito, Spring Boot Test
- **Build Tool**: Maven

### Frontend (Coming Soon)
- React/Vue/Angular
- Axios for API calls
- JWT token management
- Responsive UI design

## 🤖 AI Usage Transparency

This project was developed with the assistance of AI tools, following best practices for responsible AI usage:

### Tools Used
- **GitHub Copilot**: Code generation, test scaffolding, and boilerplate reduction
- **AI-Assisted Development**: Pattern recognition, code suggestions, and documentation

### Approach
1. **Test-First Development**: Tests were designed manually, then AI helped with implementation
2. **Code Review**: All AI-generated code was reviewed and validated
3. x] Frontend Application (React)
- [x] User Interface (Login, Register, Shop, Admin Panel)
- [x] API Integration with Axios
- [x] Cloud Database (Aiven MySQL)e logic written manually, AI used for repetitive tasks
4. **Transparency**: All commits with AI assistance include co-authorship attribution

For detailed AI usage information, see the "My AI Usage" section in [backend/README.md](backend/README.md)
Frontend README](frontend/README.md) - Frontend setup and features
- [API Documentation](backend/README.md#-api-documentation) - Complete API reference
- [Aiven Setup Guide](backend/AIVEN_SETUP.md) - Cloud database configuration
- [Postman Collection](backend/postman/Sweet-Shop-API.postman_collection.json) - Import for testing

## 🎨 Application Features

### User Features
- 🔐 User registration and login
- 🍬 Browse sweet catalog
- 🔍 Search and filter by category
- 🛒Backend Port Already in Use
- Change port in `application.properties`: `server.port=8081`
- Update frontend proxy in `vite.config.js` accordingly

### Frontend Not Connecting to Backend
- Ensure backend is running on `http://localhost:8080`
- Check proxy configuration in `frontend/vite.config.js`
- Clear browser cache and localStorage
- Check browser console for CORS errors

### Tests Failing
- Ensure H2 database is in dependencies
- Check `application-test.properties` configuration

### Frontend Build Issues
- Delete `node_modules` and run `npm install` again
- Ensure Node.js version is 16 or higher
- Clear npm cache: `npm cache clean --force`cription)
- 🗑️ Delete sweets from catalog
- 📦 Restock inventory
- 📊 View all inventory in admin panel

### Categories
- Chocolate
- Candy
- Gummy
- Cookie
- Cake
- Other

This project follows TDD (Test-Driven Development):

1. **RED**: Write a failing test
2. **GREEN**: Write minimal code to pass the test
3. **REFACTOR**: Improve code quality

### Git Commit Convention

```bash
git commit -m "feat: Add user authentication endpoint

Implemented JWT-based authentication with Spring Security.
Used GitHub Copilot for boilerplate code generation.

Co-authored-by: GitHub Copilot <copilot@github.com>"
```

## 📈 Project Status

- [x] Backend API with Spring Boot
- [x] JWT Authentication
- [x] User Management
- [x] Sweet CRUD Operations
- [x] Inventory Management (Purchase/Restock)
- [x] Role-Based Authorization
- [x] Comprehensive Testing
- [x] API Documentation
- [x] Frontend Application
- [x] Deployment Configuration
- [x] CI/CD Pipeline

## 📖 Documentation

- [Backend README](backend/README.md) - Detailed backend documentation
- [API Documentation](backend/README.md#-api-documentation) - Complete API reference
- [Postman Collection](backend/Sweet-Shop-API.postman_collection.json) - Import for testing

## 🐛 Troubleshooting

### Database Connection Issues
- Application uses Aiven MySQL (cloud-hosted, always available)
- Check your internet connection
- Verify credentials in `application.properties`
- See `backend/AIVEN_SETUP.md` for detailed troubleshooting

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`

### Tests Failing
- Ensure H2 database is in dependencies
- Check `application-test.properties` configuration

## 🤝 Contributing

This is a portfolio project, but suggestions are welcome:

1. Fork the repository
2. Create a feature branch
3. Follow TDD approach
4. Add tests before implementation
5. Document AI usage in commits
6. Submit a pull request

## 📄 License

This project is developed as part of a coding assessment.

## 👤 Author

Sandeep Vashishtha
- GitHub: sandeepvashishtha
- Email:sandeepvashishtha21@gmail.com

---

**Note**: This project demonstrates professional development practices including TDD, clean code, security best practices, and responsible AI tool usage.

