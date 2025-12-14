# Aiven MySQL Setup Guide

## ✅ Your Aiven MySQL Database is Configured!

### Connection Details

- **Host**: `sweet-shop-theskylancer99-ebcd.l.aivencloud.com`
- **Port**: `27626`
- **Database**: `defaultdb`
- **Username**: `avnadmin`
- **Password**: `AVNS_iIlY0h5otAjnXhhiiL-`
- **SSL Mode**: REQUIRED

## 🚀 Quick Start

Your application is already configured to use Aiven MySQL. Just run:

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

The application will automatically connect to your Aiven MySQL database!

## 🔧 Configuration Files Updated

### 1. `pom.xml`
- ✅ Changed from PostgreSQL to MySQL driver
- ✅ Added `mysql-connector-j` dependency

### 2. `application.properties`
- ✅ Updated to Aiven MySQL connection string
- ✅ SSL mode set to REQUIRED
- ✅ Changed dialect to MySQL

### 3. `application-test.properties`
- ✅ Still uses H2 for fast testing (no Aiven connection needed)

## 📊 Database Tables

When you first run the application, Hibernate will automatically create these tables:

1. **users** - User accounts with authentication
2. **sweets** - Sweet inventory items

### Sample Data (Dev Profile)

To start with sample data, run:

```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

This creates:
- Admin user: `admin` / `admin123`
- Regular user: `user` / `user123`
- 5 sample sweets

## 🔍 Verify Connection

### Test Database Connection

```bash
# Start the application
mvn spring-boot:run

# Check logs for:
# ✅ "HikariPool-1 - Start completed"
# ✅ "Started SweetShopApplication"
```

### View Database with MySQL Workbench

1. Download MySQL Workbench
2. Create new connection:
   - **Hostname**: `sweet-shop-theskylancer99-ebcd.l.aivencloud.com`
   - **Port**: `27626`
   - **Username**: `avnadmin`
   - **Password**: `AVNS_iIlY0h5otAjnXhhiiL-`
   - **SSL**: Use SSL/TLS (Required)

### View Database from Aiven Console

1. Go to https://console.aiven.io
2. Select your "sweet-shop" service
3. Navigate to "Tables" tab to see your data

## 🧪 Testing

Tests use H2 in-memory database (no Aiven connection):

```bash
mvn test
```

This is faster and doesn't consume your Aiven database quota.

## 🔒 Security Notes

### ⚠️ Important: Protect Your Credentials

Your database password is currently in `application.properties`. For production:

1. **Use Environment Variables**:

Create `.env` file (already in `.gitignore`):
```bash
MYSQL_URL=jdbc:mysql://sweet-shop-theskylancer99-ebcd.l.aivencloud.com:27626/defaultdb?sslMode=REQUIRED
MYSQL_USERNAME=avnadmin
MYSQL_PASSWORD=AVNS_iIlY0h5otAjnXhhiiL-
```

2. **Update `application.properties`**:
```properties
spring.datasource.url=${MYSQL_URL}
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}
```

3. **Load environment variables**:
```bash
# Windows PowerShell
$env:MYSQL_URL="jdbc:mysql://..."
$env:MYSQL_USERNAME="avnadmin"
$env:MYSQL_PASSWORD="AVNS_iIlY0h5otAjnXhhiiL-"

mvn spring-boot:run
```

### 🔐 SSL Certificate (Optional)

Aiven provides SSL by default. If you need the CA certificate:

1. Go to Aiven Console > Your Service > Overview
2. Download "CA Certificate"
3. Save as `aiven-ca.pem` in `backend/src/main/resources/`

## 📊 Aiven Dashboard Features

From https://console.aiven.io:

- **Metrics**: Monitor database performance
- **Logs**: View MySQL logs
- **Backups**: Automatic daily backups
- **Query Statistics**: See slow queries
- **Database Tables**: Browse data directly

## 🐛 Troubleshooting

### Connection Timeout
```
Error: Communications link failure
```
**Solution**: Check your internet connection and firewall settings.

### SSL Error
```
Error: SSL connection required
```
**Solution**: Ensure `sslMode=REQUIRED` is in your connection string.

### Authentication Failed
```
Error: Access denied for user
```
**Solution**: Verify username and password are correct.

### Database Not Found
```
Error: Unknown database 'defaultdb'
```
**Solution**: The database name must be `defaultdb` (Aiven default).

## 🚀 First Run Checklist

- [ ] Maven dependencies installed (`mvn clean install`)
- [ ] Application starts successfully (`mvn spring-boot:run`)
- [ ] No connection errors in logs
- [ ] Can register a user via API
- [ ] Can login and get JWT token
- [ ] Can create a sweet
- [ ] Data persists after restart

## 📝 Test Your API

### 1. Start Application
```bash
mvn spring-boot:run
```

### 2. Register User
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@example.com",
    "password": "password123"
  }'
```

### 3. Verify in Aiven
- Go to Aiven Console > Tables > users
- You should see your new user!

## 💾 Data Persistence

All data is now stored in Aiven MySQL:
- ✅ Survives application restarts
- ✅ Accessible from anywhere
- ✅ Automatically backed up
- ✅ Production-ready

## 🎓 Aiven Free Tier Limits

- **Storage**: 5 GB
- **Memory**: 1 GB RAM
- **Connections**: 25 concurrent
- **Backups**: 2 days retention

This is more than enough for development and portfolio projects!

## 🆘 Need Help?

1. Check Spring Boot logs for errors
2. Verify connection string format
3. Test connection with MySQL Workbench
4. Check Aiven service status in console

---

**Your database is ready to use! Start the application and begin testing your API.** 🎉
