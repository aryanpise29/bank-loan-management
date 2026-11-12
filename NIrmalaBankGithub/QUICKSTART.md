# 🚀 Quick Start Guide - Bank Loan Management System

## Prerequisites Checklist
- [ ] Java 25 installed
- [ ] MySQL 8 installed and running
- [ ] Maven installed (or use IDE)

## 5-Minute Setup

### 1. Configure Database (30 seconds)
Edit `src/main/resources/application.properties`:
```properties
spring.datasource.password=YOUR_MYSQL_PASSWORD
```

### 2. Build & Run (2 minutes)
```bash
mvn clean install
mvn spring-boot:run
```

### 3. Login (10 seconds)
Open browser: http://localhost:8080
- Username: `admin`
- Password: `admin123`

## First Time Usage (2 minutes)

### Step 1: Create a Loan Product
1. Click "Loan Products" → "Add New Product"
2. Fill in:
   - Product Name: Personal Loan
   - Interest Rate: 10.5
   - Min Amount: 10000
   - Max Amount: 500000
   - Tenure: 12
3. Click "Save Product"

### Step 2: Register a Customer
1. Click "Customers" → "Add New Customer"
2. Fill in customer details
3. Click "Register Customer"
4. Click "Verify KYC" for the customer

### Step 3: Apply for Loan (via API)
```bash
curl -X POST http://localhost:8080/api/loan-applications \
  -H "Content-Type: application/json" \
  -d '{
    "customerId": 1,
    "loanProductId": 1,
    "loanAmount": 100000
  }'
```

### Step 4: Approve Loan
1. Click "Applications"
2. Click "Approve" button
3. System automatically generates EMI schedule

### Step 5: View Results
1. Click "All Applications" → "View Repayments"
2. See the EMI schedule
3. Click "Reports" for analytics

## Common Commands

### Start Application
```bash
mvn spring-boot:run
```

### Build JAR
```bash
mvn clean package
java -jar target/loan-management-system-0.0.1-SNAPSHOT.jar
```

### Reset Database
```sql
DROP DATABASE bank_loan_db;
CREATE DATABASE bank_loan_db;
```

## Troubleshooting

**Problem:** Can't connect to MySQL
**Solution:** Check MySQL is running: `mysql -u root -p`

**Problem:** Port 8080 in use
**Solution:** Change port in application.properties: `server.port=8081`

**Problem:** Build fails
**Solution:** Run: `mvn clean install -U`

## What's Next?

✅ Explore the Dashboard  
✅ Try different loan products  
✅ Test the complete workflow  
✅ Check the Reports section  
✅ Use the REST API endpoints  

## Need Help?

Refer to the main README.md for detailed documentation.

---
Happy Banking! 🏦
