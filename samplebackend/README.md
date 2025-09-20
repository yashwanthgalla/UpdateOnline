# Banking Application Backend

This is a Spring Boot-based backend application for a comprehensive banking system.

## Features

- **User Authentication & Authorization**: JWT-based authentication with role-based access control
- **Account Management**: Create and manage different types of bank accounts (Savings, Current, Salary, Fixed Deposit)
- **Transaction Processing**: Transfer money, deposits, withdrawals with transaction history
- **Loan Management**: Apply for and manage different types of loans (Personal, Home, Vehicle, Education, Business, Gold)
- **Card Management**: Credit/Debit card applications and management
- **Deposit Services**: Fixed deposits and recurring deposits
- **Security**: Comprehensive security with Spring Security and JWT
- **Database**: MySQL support with JPA/Hibernate

## Technology Stack

- **Framework**: Spring Boot 3.1.5
- **Java Version**: 17
- **Database**: MySQL (with H2 for testing)
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Validation**: Bean Validation
- **Documentation**: REST API with proper response structures

## Project Structure

```
src/
├── main/
│   ├── java/com/banking/app/
│   │   ├── BankingApplication.java          # Main application class
│   │   ├── config/                          # Configuration classes
│   │   │   ├── WebSecurityConfig.java       # Security configuration
│   │   │   ├── JwtUtils.java               # JWT utility
│   │   │   └── AuthTokenFilter.java        # JWT authentication filter
│   │   ├── controller/                      # REST controllers
│   │   │   ├── AuthController.java         # Authentication endpoints
│   │   │   ├── AccountController.java      # Account management
│   │   │   ├── TransactionController.java  # Transaction handling
│   │   │   ├── UserController.java         # User profile management
│   │   │   └── HomeController.java         # General endpoints
│   │   ├── dto/                            # Data Transfer Objects
│   │   │   ├── LoginRequest.java
│   │   │   ├── SignupRequest.java
│   │   │   ├── AuthResponse.java
│   │   │   └── ApiResponse.java
│   │   ├── entity/                         # JPA Entities
│   │   │   ├── User.java
│   │   │   ├── Account.java
│   │   │   ├── Transaction.java
│   │   │   ├── Loan.java
│   │   │   ├── Card.java
│   │   │   └── Deposit.java
│   │   ├── repository/                     # Data repositories
│   │   │   ├── UserRepository.java
│   │   │   ├── AccountRepository.java
│   │   │   ├── TransactionRepository.java
│   │   │   ├── LoanRepository.java
│   │   │   ├── CardRepository.java
│   │   │   └── DepositRepository.java
│   │   └── service/                        # Business logic services
│   │       ├── AuthService.java
│   │       ├── AccountService.java
│   │       ├── TransactionService.java
│   │       ├── CustomUserDetailsService.java
│   │       └── UserPrincipal.java
│   └── resources/
│       └── application.properties          # Application configuration
└── test/                                   # Test classes
```

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ (or use H2 for testing)

### Database Setup
1. Install MySQL and create a database named `banking_db`
2. Update database credentials in `application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

### Running the Application

1. **Clone and navigate to the backend directory**:
   ```bash
   cd samplebackend
   ```

2. **Build the project**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn spring-boot:run
   ```

4. **Alternative - Run the JAR**:
   ```bash
   java -jar target/banking-app-0.0.1-SNAPSHOT.jar
   ```

The application will start on `http://localhost:8080/api`

## API Endpoints

### Authentication
- `POST /api/auth/signup` - Register a new user
- `POST /api/auth/signin` - User login
- `GET /api/auth/test` - Test authentication endpoint

### User Management
- `GET /api/user/profile` - Get user profile (authenticated)
- `PUT /api/user/profile` - Update user profile (authenticated)

### Account Management
- `POST /api/accounts/create` - Create new account (authenticated)
- `GET /api/accounts/my-accounts` - Get user accounts (authenticated)
- `GET /api/accounts/{accountNumber}` - Get account details
- `PUT /api/accounts/{accountNumber}/deactivate` - Deactivate account

### Transactions
- `POST /api/transactions/transfer` - Transfer money between accounts
- `POST /api/transactions/deposit` - Deposit money to account
- `POST /api/transactions/withdraw` - Withdraw money from account
- `GET /api/transactions/account/{accountNumber}` - Get account transactions
- `GET /api/transactions/account/{accountNumber}/between-dates` - Get transactions between dates

### General
- `GET /api/` - API information and available endpoints
- `GET /api/health` - Health check endpoint

## Sample Requests

### User Registration
```json
POST /api/auth/signup
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "1234567890",
  "address": "123 Main St",
  "panNumber": "ABCDE1234F",
  "aadharNumber": "123456789012"
}
```

### User Login
```json
POST /api/auth/signin
{
  "email": "user@example.com",
  "password": "password123"
}
```

### Create Account
```json
POST /api/accounts/create?accountType=SAVINGS
Authorization: Bearer <jwt_token>
```

### Money Transfer
```json
POST /api/transactions/transfer
?fromAccountNumber=ACC123&toAccountNumber=ACC456&amount=1000&description=Payment
Authorization: Bearer <jwt_token>
```

## Configuration

Key configuration properties in `application.properties`:

```properties
# Server Configuration
server.port=8080
server.servlet.context-path=/api

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/banking_db
spring.datasource.username=root
spring.datasource.password=password

# JWT Configuration
jwt.secret=mySecretKey
jwt.expiration=86400000

# CORS Configuration
cors.allowed-origins=http://localhost:3000,http://localhost:5173
```

## Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt password hashing
- **CORS Configuration**: Configurable for frontend integration
- **Role-based Access**: Support for different user roles (CUSTOMER, ADMIN, MANAGER)
- **Input Validation**: Comprehensive validation using Bean Validation

## Database Schema

The application uses the following main entities:
- **Users**: Customer information and authentication
- **Accounts**: Bank accounts with different types
- **Transactions**: All financial transactions
- **Loans**: Loan applications and management
- **Cards**: Credit/Debit card information
- **Deposits**: Fixed and recurring deposits

## Testing

Run tests using:
```bash
mvn test
```

## Development

For development mode, the application includes:
- Hot reload with Spring Boot DevTools
- H2 database console for testing
- Detailed logging configuration
- Profile-based configuration support

## Frontend Integration

This backend is designed to work with the React frontend. Make sure to:
1. Update CORS origins in `application.properties`
2. Configure proper base URL in frontend API calls
3. Handle JWT tokens in frontend requests

## Troubleshooting

Common issues and solutions:

1. **Database Connection Issues**: Verify MySQL is running and credentials are correct
2. **JWT Errors**: Check JWT secret and expiration settings
3. **CORS Issues**: Verify allowed origins in security configuration
4. **Port Conflicts**: Change server port in application.properties

## Contributing

1. Follow Java naming conventions
2. Add proper validation to DTOs
3. Include proper error handling
4. Write unit tests for new features
5. Update documentation for API changes