# Bank Loan Management System

A comprehensive web-based application for managing bank loans, built with Spring Boot, MySQL, and Thymeleaf.

##  Features

### Core Modules
1. **Customer Management** - Register customers, manage KYC verification
2. **Loan Product Management** - Create and manage loan products (Personal, Home, Vehicle loans)
3. **Loan Application Management** - Process loan applications with approval workflow
4. **Repayment Management** - Automatic EMI calculation and repayment tracking
5. **Reporting & Auditing** - Comprehensive reports and analytics

### Key Capabilities
-  Single Admin Authentication
-  Customer Registration with KYC Verification
-  Multiple Loan Products with Different Terms
-  Loan Application Approval/Rejection Workflow
-  Automatic EMI Calculation & Schedule Generation
-  Repayment Tracking & Outstanding Balance
-  Comprehensive Dashboard & Reports
-  RESTful API Support

## Technology Stack

- **Backend**: Java 25, Spring Boot 3.2.0
- **Security**: Spring Security
- **Database**: MySQL 8
- **ORM**: Spring Data JPA (Hibernate)
- **Frontend**: Thymeleaf, HTML5, CSS3
- **Build Tool**: Maven
- **Additional**: Lombok, Jakarta Validation

##  Prerequisites

Before running this application, ensure you have:

1. **Java Development Kit (JDK) 17** or higher
2. **MySQL 8.0** or higher
3. **Maven 3.6+** (or use IDE's built-in Maven)
4. **IDE** (Eclipse, IntelliJ IDEA, or VS Code)

##  Installation & Setup

### Step 1: Clone the Repository
```bash
git clone <repository-url>
cd Bank-loan-master
```

### Step 2: Setup MySQL Database
```sql
-- Start MySQL server
-- Create database (or let the app auto-create it)
CREATE DATABASE bank_loan_db;
```

### Step 3: Configure Database Connection

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_loan_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### Step 4: Build the Application
```bash
mvn clean install
```

### Step 5: Run the Application

**Option A: Using Maven**
```bash
mvn spring-boot:run
```

**Option B: Using IDE**
- Right-click on `BankLoanApplication.java`
- Select "Run As → Java Application"

**Option C: Using JAR**
```bash
java -jar target/loan-management-system-0.0.1-SNAPSHOT.jar
```

### Step 6: Access the Application

Open your browser and navigate to:
```
http://localhost:8080
```

**Default Admin Credentials:**
- Username: `admin`
- Password: `admin123`

##  Database Schema

The application automatically creates the following tables:

1. **admin** - Admin user authentication
2. **customer** - Customer information and KYC status
3. **loan_product** - Loan product details
4. **loan_application** - Loan applications
5. **repayment** - EMI repayment schedule

##  Usage Guide

### 1. Login
- Access http://localhost:8080
- Login with admin credentials

### 2. Register Customer
- Navigate to "Customers" → "Add New Customer"
- Fill in customer details
- Click "Register Customer"

### 3. Verify KYC
- Go to "Customers"
- Click "Verify KYC" for pending customers

### 4. Create Loan Product
- Navigate to "Loan Products" → "Add New Product"
- Enter product details (name, interest rate, amount range, tenure)
- Click "Save Product"

### 5. Apply for Loan (via API)
```bash
POST http://localhost:8080/api/loan-applications
Content-Type: application/json

{
  "customerId": 1,
  "loanProductId": 1,
  "loanAmount": 100000
}
```

### 6. Approve/Reject Loan
- Navigate to "Applications"
- Click "Approve" or "Reject" for pending applications
- System automatically generates EMI schedule on approval

### 7. View Repayment Schedule
- Go to "All Applications"
- Click "View Repayments" for approved loans

### 8. Generate Reports
- Navigate to "Reports"
- View comprehensive statistics and analytics

##  REST API Endpoints

### Customer Management
```
POST   /api/customers                    - Register customer
GET    /api/customers/{id}               - Get customer details
PUT    /api/customers/{id}               - Update customer
PUT    /api/customers/{id}/kyc-status    - Update KYC status
GET    /api/customers                    - Get all customers
```

### Loan Product Management
```
POST   /api/loan-products                - Add loan product
PUT    /api/loan-products/{id}           - Update product
GET    /api/loan-products/{id}           - Get product details
GET    /api/loan-products                - Get all products
GET    /api/loan-products/eligible       - Get eligible products
```

### Loan Application Management
```
POST   /api/loan-applications            - Apply for loan
GET    /api/loan-applications/{id}       - Get application status
PUT    /api/loan-applications/{id}/process - Approve/Reject
GET    /api/loan-applications/customer/{id} - Get customer applications
GET    /api/loan-applications/status/{status} - Filter by status
```

### Repayment Management
```
GET    /api/repayments/schedule/{id}     - Get repayment schedule
POST   /api/repayments/payment           - Make payment
GET    /api/repayments/outstanding/{id}  - Get outstanding balance
GET    /api/repayments/pending/{id}      - Get pending payments
GET    /api/repayments/completed/{id}    - Get completed payments
```

### Reports
```
GET    /api/reports/loans                - Loan report
GET    /api/reports/repayments           - Repayment report
GET    /api/reports/outstanding          - Outstanding loans report
GET    /api/reports/customer-portfolio/{id} - Customer portfolio
```

##  Project Structure

```
Bank-loan-master/
├── src/main/java/com/bank/loan/
│   ├── config/              # Security & initialization configs
│   ├── controller/          # REST & Web controllers
│   ├── dto/                 # Data Transfer Objects
│   ├── exception/           # Custom exceptions & handlers
│   ├── model/               # JPA entities
│   ├── repository/          # Data access layer
│   ├── service/             # Business logic layer
│   └── BankLoanApplication.java
├── src/main/resources/
│   ├── templates/           # Thymeleaf HTML templates
│   └── application.properties
├── pom.xml
└── README.md
```

##  Security Features

- Spring Security with form-based authentication
- BCrypt password encoding
- Single admin user (can be extended)
- CSRF protection (disabled for API simplicity)
- Session management
