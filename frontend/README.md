# Sweet Shop Management System - Frontend

A modern, responsive React-based frontend for the Sweet Shop Management System. This application provides a beautiful user interface for customers to browse and purchase sweets, and for administrators to manage inventory.

![Sweet Shop Banner](https://via.placeholder.com/1200x300/4CAF50/FFFFFF?text=Sweet+Shop+Management+System)

## 🎯 Features

### User Features
- **User Authentication**: Secure registration and login system with JWT tokens
- **Sweet Catalog**: Browse all available sweets with detailed information
- **Search & Filter**: Find sweets by name, description, or category
- **Category Filtering**: Filter sweets by CHOCOLATE, CANDY, GUMMY, COOKIE, CAKE, or OTHER
- **Purchase System**: Buy sweets with real-time stock updates
- **Responsive Design**: Works seamlessly on desktop, tablet, and mobile devices

### Admin Features
- **Inventory Management**: Add, edit, and delete sweets
- **Stock Control**: Restock sweets with custom quantities
- **Low Stock Alerts**: Visual indicators for items running low
- **Comprehensive Dashboard**: View and manage all sweets in one place

## 🚀 Technologies Used

- **React 19.2.0**: Modern UI library for building interactive interfaces
- **React Router DOM**: Client-side routing for single-page application navigation
- **Axios**: Promise-based HTTP client for API requests
- **Vite**: Next-generation frontend build tool for fast development
- **CSS3**: Modern, responsive styling with CSS variables and animations

## 📋 Prerequisites

Before running this application, make sure you have:

- Node.js (v16 or higher)
- npm or yarn package manager
- Access to the backend API (running locally or deployed)

## 🛠️ Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd Sweet-Shop-Management-System/frontend
```

### 2. Install Dependencies

```bash
npm install
```

### 3. Configure Backend API

The application is configured to connect to the Azure-hosted backend:

```javascript
// src/services/api.js
const API_BASE_URL = 'https://sweet-shop-backend-bggngwcufvhfapg3.westindia-01.azurewebsites.net/api';
```

If you need to change the backend URL, update this value in `src/services/api.js`.

### 4. Run the Development Server

```bash
npm run dev
```

The application will start at `http://localhost:5173` (or another port if 5173 is busy).

### 5. Build for Production

```bash
npm run build
```

This creates an optimized production build in the `dist` folder.

### 6. Preview Production Build

```bash
npm run preview
```

## 📁 Project Structure

```
frontend/
├── public/              # Static assets
├── src/
│   ├── assets/         # Images and other assets
│   ├── components/     # React components
│   │   ├── AdminPanel.jsx      # Admin dashboard for managing sweets
│   │   ├── Login.jsx           # User login form
│   │   ├── Navbar.jsx          # Navigation bar
│   │   ├── Register.jsx        # User registration form
│   │   └── SweetList.jsx       # Main sweet catalog view
│   ├── context/        # React context providers
│   │   └── AuthContext.jsx     # Authentication state management
│   ├── services/       # API service layer
│   │   └── api.js              # Axios configuration and API calls
│   ├── App.css         # Application-specific styles
│   ├── App.jsx         # Main app component with routing
│   ├── index.css       # Global styles and CSS variables
│   └── main.jsx        # Application entry point
├── index.html          # HTML template
├── package.json        # Dependencies and scripts
├── vite.config.js      # Vite configuration
└── README.md          # This file
```

## 🎨 Key Components

### 1. Authentication Flow

The app uses JWT tokens stored in localStorage for authentication:

- **Register**: New users create an account with username, email, and password
- **Login**: Existing users log in with username and password
- **Protected Routes**: Certain routes require authentication
- **Admin Routes**: Admin panel is accessible only to users with ADMIN role

### 2. Sweet List (Main Shop)

- Displays all sweets in a responsive grid layout
- Real-time search functionality
- Category filtering dropdown
- Purchase button (disabled when out of stock)
- Automatic quantity updates after purchase

### 3. Admin Panel

- Table view of all sweets
- Add new sweets with a modal form
- Edit existing sweet details
- Delete sweets with confirmation
- Restock functionality
- Low stock visual indicators (< 10 items)

## 🔌 API Integration

### API Endpoints Used

```javascript
// Authentication
POST /api/auth/register    // User registration
POST /api/auth/login       // User login

// Sweets (Protected)
GET  /api/sweets          // Get all sweets
GET  /api/sweets/:id      // Get sweet by ID
POST /api/sweets          // Create new sweet (Admin)
PUT  /api/sweets/:id      // Update sweet (Admin)
DELETE /api/sweets/:id    // Delete sweet (Admin)

// Inventory
POST /api/sweets/:id/purchase  // Purchase sweet
POST /api/sweets/:id/restock   // Restock sweet (Admin)
GET  /api/sweets/search        // Search sweets
```

### Authentication

All protected endpoints require a JWT token in the Authorization header:

```
Authorization: Bearer <token>
```

The axios interceptor automatically adds this header for authenticated requests.

## 🎭 User Roles

### Regular User
- Browse sweets
- Search and filter
- Purchase sweets

### Admin User
- All regular user permissions
- Access to Admin Panel
- Add new sweets
- Edit sweet details
- Delete sweets
- Restock inventory

## 🖼️ Screenshots

### Login Page
![Login](https://via.placeholder.com/800x500/FFFFFF/4CAF50?text=Login+Page)

### Sweet Shop (Main Catalog)
![Sweet List](https://via.placeholder.com/800x500/FFFFFF/4CAF50?text=Sweet+Shop+Catalog)

### Admin Panel
![Admin Panel](https://via.placeholder.com/800x500/FFFFFF/4CAF50?text=Admin+Panel)

### Add/Edit Sweet Modal
![Modal](https://via.placeholder.com/800x500/FFFFFF/4CAF50?text=Add+Sweet+Modal)

## 🎨 Design Features

- **Modern UI**: Clean, professional design with smooth animations
- **Responsive Layout**: Adapts to all screen sizes (mobile, tablet, desktop)
- **Color Scheme**: 
  - Primary: #4CAF50 (Green)
  - Secondary: #FF9800 (Orange)
  - Danger: #f44336 (Red)
  - Warning: #ff9800 (Orange)
- **Accessibility**: Proper form labels, focus states, and semantic HTML
- **User Feedback**: 
  - Loading spinners during API calls
  - Success/error alerts
  - Disabled buttons when appropriate
  - Hover effects and transitions

## 🧪 Testing

To test the application:

1. **Register** a new user account
2. **Login** with your credentials
3. **Browse** the sweet catalog
4. **Search** for specific sweets
5. **Filter** by category
6. **Purchase** a sweet (quantity will decrease)
7. **Logout** and login as admin (if you have admin credentials)
8. **Access** the Admin Panel
9. **Add** a new sweet
10. **Edit** an existing sweet
11. **Restock** inventory
12. **Delete** a sweet

### Testing Credentials

Ask your backend administrator for test credentials:
- Regular user credentials
- Admin user credentials

## 🔧 Troubleshooting

### Common Issues

**Problem**: Application won't start
- **Solution**: Make sure you've run `npm install` and have Node.js installed

**Problem**: Can't connect to backend
- **Solution**: Verify the backend URL in `src/services/api.js` and ensure the backend is running

**Problem**: Login fails with 401
- **Solution**: Check your credentials and ensure the backend is accessible

**Problem**: Admin features not showing
- **Solution**: Ensure your user account has the ADMIN role in the backend database

## 📱 Responsive Breakpoints

- **Mobile**: < 480px
- **Tablet**: 480px - 768px
- **Desktop**: > 768px

## 🚀 Deployment

### Deploy to Vercel

1. Install Vercel CLI: `npm i -g vercel`
2. Run: `vercel`
3. Follow the prompts

### Deploy to Netlify

1. Build the project: `npm run build`
2. Drag and drop the `dist` folder to Netlify

### Environment Variables

No environment variables are currently required as the API URL is hardcoded. For production, consider using environment variables:

```bash
VITE_API_BASE_URL=https://your-backend-url.com/api
```

Then update `src/services/api.js`:

```javascript
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
```

## 🤝 My AI Usage

### AI Tools Used

I extensively used **GitHub Copilot** and **AI-assisted development** to build this frontend application. Here's how AI contributed to the development process:

#### 1. **Initial Project Setup**
- AI helped scaffold the React application structure
- Suggested optimal folder organization for components, services, and context
- Recommended modern React patterns (hooks, context API)

#### 2. **Component Development**
- **Login & Register Components**: AI generated the initial form structure and validation logic
- **SweetList Component**: AI assisted with the grid layout, search/filter logic, and responsive design
- **AdminPanel Component**: AI helped create the table structure, modal forms, and CRUD operations
- **Navbar Component**: AI suggested the layout and navigation patterns

#### 3. **State Management**
- AI helped design the AuthContext for managing authentication state
- Suggested using localStorage for token persistence
- Recommended the useContext hook pattern for accessing auth state

#### 4. **API Integration**
- AI assisted in setting up Axios with interceptors
- Suggested the service layer pattern for organizing API calls
- Helped implement automatic token injection in requests

#### 5. **Styling & Design**
- AI generated the initial CSS structure with CSS variables
- Suggested color schemes and modern design patterns
- Helped create responsive breakpoints and animations
- Recommended accessibility improvements

#### 6. **Routing & Navigation**
- AI helped implement React Router with protected routes
- Suggested the route structure and navigation patterns
- Assisted with role-based access control (admin vs regular user)

#### 7. **Error Handling & User Feedback**
- AI suggested patterns for displaying errors and success messages
- Helped implement loading states and spinners
- Recommended user-friendly error messages

#### 8. **Code Optimization**
- AI identified opportunities to reduce code duplication
- Suggested performance optimizations (useEffect dependencies, etc.)
- Helped refactor components for better maintainability

### Reflection on AI Impact

**Positive Impacts:**
- **Faster Development**: AI significantly reduced the time needed to build components and features
- **Best Practices**: AI consistently suggested modern React patterns and best practices
- **Problem Solving**: When stuck, AI provided alternative approaches to solve problems
- **Code Quality**: AI helped maintain consistent code style and structure

**Learning Opportunities:**
- I reviewed all AI-generated code to understand the patterns being used
- AI explanations helped me learn new React concepts and patterns
- I modified AI suggestions to fit the specific requirements of this project

**Human Oversight:**
- All AI suggestions were reviewed and tested before implementation
- I made architectural decisions and selected which AI suggestions to use
- I customized components to match the specific project requirements
- I ensured all code works correctly with the backend API

### Specific AI Contributions

| Component/Feature | AI Contribution | Human Contribution |
|-------------------|----------------|-------------------|
| Project Structure | Generated initial structure | Organized and refined |
| Authentication | Generated JWT handling logic | Integrated with backend |
| Sweet List | Generated grid and filter UI | Added search logic |
| Admin Panel | Generated CRUD operations | Connected to API |
| Styling | Generated CSS framework | Customized colors and design |
| Routing | Suggested route structure | Implemented protection |
| API Service | Generated Axios setup | Configured for Azure backend |

**Conclusion**: AI tools were instrumental in accelerating development and maintaining code quality. However, human oversight, decision-making, and testing were essential to ensure the application met all requirements and worked correctly with the backend API.

## 📄 License

This project is part of a technical assessment and is intended for educational purposes.

## 👥 Author

Built as part of the Sweet Shop Management System TDD Kata project.

## 🙏 Acknowledgments

- React team for the amazing framework
- Vite team for the blazing-fast build tool
- Backend team for the robust API

---

**Happy Shopping! 🍬**
