# Environment Setup Guide

## Backend Setup

1. **Copy the environment file**:
   ```bash
   cd backend
   cp .env.example .env
   ```

2. **Configure your database credentials in `.env`**:
   ```
   DB_URL=jdbc:mysql://your-host:port/database?sslMode=REQUIRED
   DB_USERNAME=your-username
   DB_PASSWORD=your-password
   JWT_SECRET=your-secure-secret-key
   ```

3. **Generate a secure JWT secret** (optional):
   ```bash
   # On Linux/Mac
   openssl rand -hex 32
   
   # On Windows PowerShell
   -join ((65..90) + (97..122) + (48..57) | Get-Random -Count 32 | ForEach-Object {[char]$_})
   ```

## Frontend Setup

No environment variables needed for basic setup. The frontend uses the backend proxy configured in `vite.config.js`.

## Important Notes

- **Never commit `.env` files** - they contain sensitive credentials
- The `.env.example` files show the required variables but with placeholder values
- Make sure `.env` is in `.gitignore`
- For production, use secure environment variable management (AWS Secrets Manager, Azure Key Vault, etc.)

## Running the Application

After configuring environment variables:

**Backend**:
```bash
cd backend
mvn spring-boot:run
```

**Frontend**:
```bash
cd frontend
npm run dev
```
