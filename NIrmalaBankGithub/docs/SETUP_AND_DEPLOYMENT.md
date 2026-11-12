# Bank Loan Management System - Setup and Deployment Guide

## Prerequisites

Before setting up the Bank Loan Management System, ensure you have the following software installed:

### Required Software
- **Java 25** - [Download from Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Maven 3.6+** - [Download from Apache Maven](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - [Download from MySQL](https://dev.mysql.com/downloads/mysql/)
- **Git** (optional) - [Download from Git](https://git-scm.com/downloads)

### Verify Installation
```bash
java -version
mvn -version
mysql --version
```

## Database Setup

### 1. Install and Start MySQL
- Install MySQL 8.0 or higher
- Start MySQL service
- Create a root user with password (or use existing credentials)

### 2. Create Database
Connect to MySQL and create the database:
```sql
mysql -u root -p
CREATE DATABASE bank_loan_db;
SHOW DATABASES;
EXIT;
```

### 3. Configure Database Connection
The application will automatically create tables using Hibernate DDL. Ensure your MySQL credentials are correctly set in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bank_loan_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=your_password_here
```

## Application Setup

### 1. Clone/Download the Project
```bash
# If using Git
git clone <repository-url>
cd Bank_Project

# Or extract the project files to Bank_Project directory
```

### 2. Build the Application
```bash
# Navigate to project directory
cd Bank_Project

# Clean and compile the project
mvn clean compile

# Run tests (optional)
mvn test

# Package the application
mvn package
```

### 3. Run the Application
```bash
# Option 1: Using Maven
mvn spring-boot:run

# Option 2: Using JAR file
java -jar target/loan-management-system-0.0.1-SNAPSHOT.jar

# Option 3: Using IDE
# Import as Maven project and run BankLoanApplication.java
```

### 4. Verify Application Startup
- Application will start on port 8080
- Check console for "Started BankLoanApplication" message
- Database tables will be created automatically
- Visit: http://localhost:8080 (should show Whitelabel Error Page - this is normal for API-only application)

## Testing the API

### 1. Using cURL Commands

#### Register a Customer
```bash
curl -X POST http://localhost:8080/api/customers \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "1234567890",
    "address": "123 Main Street, City, State"
  }'
```

#### Update KYC Status
```bash
curl -X PUT "http://localhost:8080/api/customers/1/kyc-status?kycStatus=VERIFIED"
```

#### Add a Loan Product
```bash
curl -X POST http://localhost:8080/api/loan-products \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Personal Loan",
    "interestRate": 12.5,
    "minAmount": 10000.00,
    "maxAmount": 500000.00,
    "tenure": 24
  }'
```

#### Apply for a Loan
```bash
curl -X POST http://localhost:8080/api/loan-applications \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "loanProductId": 1,
    "loanAmount": 50000.00
  }'
```

#### Process Loan Application
```bash
curl -X PUT "http://localhost:8080/api/loan-applications/1/process?status=APPROVED"
```

#### Get Repayment Schedule
```bash
curl -X GET http://localhost:8080/api/repayments/schedule/1
```

#### Make a Payment
```bash
curl -X POST http://localhost:8080/api/repayments/payment \
  -H "Content-Type: application/json" \
  -d '{
    "repaymentId": 1,
    "paymentAmount": 2500.00,
    "paymentDate": "2024-02-15"
  }'
```

#### Generate Reports
```bash
# Loan Report
curl -X GET http://localhost:8080/api/reports/loans

# Repayment Report
curl -X GET http://localhost:8080/api/reports/repayments

# Outstanding Loans Report
curl -X GET http://localhost:8080/api/reports/outstanding

# Customer Portfolio Report
curl -X GET http://localhost:8080/api/reports/customer-portfolio/1
```

### 2. Using Postman

1. **Import Collection**: Create a new Postman collection
2. **Set Base URL**: `http://localhost:8080/api`
3. **Add Requests**: Create requests for each endpoint
4. **Test Workflow**: Follow the complete loan lifecycle

### 3. Using Swagger UI (if enabled)

If you add Swagger dependency, you can access the API documentation at:
```
http://localhost:8080/swagger-ui.html
```

## Complete Test Workflow

### Step-by-Step Testing

1. **Register Customer**
   ```bash
   curl -X POST http://localhost:8080/api/customers \
     -H "Content-Type: application/json" \
     -d '{"name": "John Doe", "email": "john@example.com", "phone": "1234567890", "address": "123 Main St"}'
   ```

2. **Verify KYC**
   ```bash
   curl -X PUT "http://localhost:8080/api/customers/1/kyc-status?kycStatus=VERIFIED"
   ```

3. **Add Loan Product**
   ```bash
   curl -X POST http://localhost:8080/api/loan-products \
     -H "Content-Type: application/json" \
     -d '{"productName": "Personal Loan", "interestRate": 12.5, "minAmount": 10000.00, "maxAmount": 500000.00, "tenure": 24}'
   ```

4. **Apply for Loan**
   ```bash
   curl -X POST http://localhost:8080/api/loan-applications \
     -H "Content-Type: application/json" \
     -d '{"customerId": 1, "loanProductId": 1, "loanAmount": 50000.00}'
   ```

5. **Approve Loan**
   ```bash
   curl -X PUT "http://localhost:8080/api/loan-applications/1/process?status=APPROVED"
   ```

6. **Check Repayment Schedule**
   ```bash
   curl -X GET http://localhost:8080/api/repayments/schedule/1
   ```

7. **Make First Payment**
   ```bash
   curl -X POST http://localhost:8080/api/repayments/payment \
     -H "Content-Type: application/json" \
     -d '{"repaymentId": 1, "paymentAmount": 2500.00, "paymentDate": "2024-02-15"}'
   ```

8. **Check Outstanding Balance**
   ```bash
   curl -X GET http://localhost:8080/api/repayments/outstanding/1
   ```

9. **Generate Reports**
   ```bash
   curl -X GET http://localhost:8080/api/reports/loans
   curl -X GET http://localhost:8080/api/reports/repayments
   curl -X GET http://localhost:8080/api/reports/outstanding
   ```

## Troubleshooting

### Common Issues

#### 1. Database Connection Error
```
Error: Could not create connection to database server
```
**Solution**: 
- Verify MySQL is running
- Check database credentials in `application.properties`
- Ensure database `bank_loan_db` exists

#### 2. Port Already in Use
```
Error: Port 8080 was already in use
```
**Solution**:
- Change port in `application.properties`: `server.port=8081`
- Or stop the process using port 8080

#### 3. Maven Build Failure
```
Error: Failed to execute goal
```
**Solution**:
- Check Java version: `java -version`
- Clean and rebuild: `mvn clean install`
- Check internet connection for dependency downloads

#### 4. Table Creation Issues
```
Error: Table 'customer' doesn't exist
```
**Solution**:
- Check Hibernate DDL setting: `spring.jpa.hibernate.ddl-auto=update`
- Verify database permissions
- Check MySQL version compatibility

### Logs and Debugging

#### Enable Debug Logging
Add to `application.properties`:
```properties
logging.level.com.bank.loan=DEBUG
logging.level.org.springframework.web=DEBUG
logging.level.org.hibernate.SQL=DEBUG
```

#### Check Application Logs
- Console output shows startup progress
- Look for "Started BankLoanApplication" message
- Check for any error messages or stack traces

## Production Deployment

### Environment Configuration

1. **Update Database Settings**
   ```properties
   spring.datasource.url=jdbc:mysql://your-db-host:3306/bank_loan_db
   spring.datasource.username=your-username
   spring.datasource.password=your-secure-password
   ```

2. **Security Considerations**
   - Change default database credentials
   - Implement proper authentication/authorization
   - Use HTTPS in production
   - Configure proper logging

3. **Performance Tuning**
   - Adjust JVM memory settings
   - Configure connection pooling
   - Enable caching if needed

### Deployment Options

1. **JAR Deployment**
   ```bash
   mvn clean package
   java -jar target/loan-management-system-0.0.1-SNAPSHOT.jar
   ```

2. **Docker Deployment** (if Dockerfile is created)
   ```bash
   docker build -t bank-loan-system .
   docker run -p 8080:8080 bank-loan-system
   ```

3. **Cloud Deployment**
   - Deploy to AWS, Azure, or Google Cloud
   - Use managed database services
   - Configure load balancers and monitoring

## Support and Maintenance

### Monitoring
- Monitor application logs
- Set up database monitoring
- Track API response times
- Monitor memory and CPU usage

### Backup Strategy
- Regular database backups
- Application configuration backups
- Log file management

### Updates and Maintenance
- Regular dependency updates
- Security patches
- Performance optimizations
- Feature enhancements

For additional support or questions, refer to the API documentation and module workflows documentation.
