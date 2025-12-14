# Sweet Shop Management System - Setup Script
# This script helps set up the backend environment on Windows

Write-Host "=== Sweet Shop Management System - Backend Setup ===" -ForegroundColor Green
Write-Host ""

# Check Java
Write-Host "Checking Java installation..." -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version" | ForEach-Object { $_.Line }
    Write-Host "✓ Java found: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Java not found. Please install Java 17 or higher." -ForegroundColor Red
    exit 1
}

# Check Maven
Write-Host "Checking Maven installation..." -ForegroundColor Yellow
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven" | ForEach-Object { $_.Line }
    Write-Host "✓ Maven found: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "✗ Maven not found. Please install Maven 3.6+." -ForegroundColor Red
    exit 1
}

# Check PostgreSQL
Write-Host "Checking PostgreSQL installation..." -ForegroundColor Yellow
try {
    $pgVersion = psql --version 2>&1
    Write-Host "✓ PostgreSQL found: $pgVersion" -ForegroundColor Green
} catch {
    Write-Host "⚠ PostgreSQL not found in PATH. Make sure it's installed." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "=== Database Setup ===" -ForegroundColor Green
Write-Host "Please create a PostgreSQL database named 'sweet_shop_db'" -ForegroundColor Cyan
Write-Host "Run this SQL command:"
Write-Host "  CREATE DATABASE sweet_shop_db;" -ForegroundColor White
Write-Host ""

$dbSetup = Read-Host "Have you created the database? (y/n)"
if ($dbSetup -ne "y") {
    Write-Host "Please create the database first and run this script again." -ForegroundColor Yellow
    exit 0
}

# Update application.properties
Write-Host ""
Write-Host "=== Configuring Database Connection ===" -ForegroundColor Green
$dbUser = Read-Host "Enter PostgreSQL username (default: postgres)"
if ([string]::IsNullOrWhiteSpace($dbUser)) { $dbUser = "postgres" }

$dbPass = Read-Host "Enter PostgreSQL password" -AsSecureString
$dbPassPlain = [Runtime.InteropServices.Marshal]::PtrToStringAuto(
    [Runtime.InteropServices.Marshal]::SecureStringToBSTR($dbPass))

# Create .env or update application.properties
$appProps = "src\main\resources\application.properties"
if (Test-Path $appProps) {
    (Get-Content $appProps) | ForEach-Object {
        $_ -replace 'spring.datasource.username=.*', "spring.datasource.username=$dbUser" `
           -replace 'spring.datasource.password=.*', "spring.datasource.password=$dbPassPlain"
    } | Set-Content $appProps
    Write-Host "✓ Database configuration updated" -ForegroundColor Green
}

# Install dependencies
Write-Host ""
Write-Host "=== Installing Dependencies ===" -ForegroundColor Green
mvn clean install -DskipTests
if ($LASTEXITCODE -eq 0) {
    Write-Host "✓ Dependencies installed successfully" -ForegroundColor Green
} else {
    Write-Host "✗ Failed to install dependencies" -ForegroundColor Red
    exit 1
}

# Run tests
Write-Host ""
Write-Host "=== Running Tests ===" -ForegroundColor Green
$runTests = Read-Host "Do you want to run tests? (y/n)"
if ($runTests -eq "y") {
    mvn test
    if ($LASTEXITCODE -eq 0) {
        Write-Host "✓ All tests passed!" -ForegroundColor Green
    } else {
        Write-Host "⚠ Some tests failed. Check the output above." -ForegroundColor Yellow
    }
}

Write-Host ""
Write-Host "=== Setup Complete! ===" -ForegroundColor Green
Write-Host ""
Write-Host "To start the application, run:" -ForegroundColor Cyan
Write-Host "  mvn spring-boot:run" -ForegroundColor White
Write-Host ""
Write-Host "The API will be available at: http://localhost:8080" -ForegroundColor Cyan
Write-Host ""
Write-Host "Default test credentials:" -ForegroundColor Yellow
Write-Host "  Admin - username: admin, password: admin123" -ForegroundColor White
Write-Host "  User  - username: user, password: user123" -ForegroundColor White
Write-Host ""
Write-Host "API Documentation: See README.md" -ForegroundColor Cyan
