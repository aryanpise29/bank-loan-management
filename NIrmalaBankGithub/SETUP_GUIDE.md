# 🏦 Bank Loan Management System - Setup Guide

## 📦 Quick Start (3 Simple Steps!)

### Step 1: Install Required Software

**1. Java 25**
- Download: https://www.oracle.com/java/technologies/downloads/#java17
- Install and verify: Open CMD → Type `java -version`

**2. MySQL 8**
- Download: https://dev.mysql.com/downloads/mysql/
- During installation: Set root password (e.g., "root123")
- Start MySQL service after installation

**3. Spring Tool Suite (STS) - Optional**
- Download: https://spring.io/tools
- OR use Eclipse/IntelliJ IDEA
- OR run from command line with Maven

---

### Step 2: Setup Database

**Open MySQL Command Line or MySQL Workbench:**

```sql
CREATE DATABASE bank_loan_db;
```

**That's it! The application will create all tables automatically.**

---

### Step 3: Configure & Run

**A. Configure Database Password:**

1. Open: `src/main/resources/application.properties`
2. Change this line:
   ```properties
   spring.datasource.password=YOUR_MYSQL_PASSWORD
   ```
   Example: If your MySQL password is "root123", change to:
   ```properties
   spring.datasource.password=root123
   ```
3. Save the file

**B. Run the Application:**

**Option 1 - Using STS/Eclipse:**
1. File → Import → Maven → Existing Maven Projects
2. Browse to `Bank-loan-master` folder → Finish
3. Wait for dependencies to download (first time only)
4. Right-click `BankLoanApplication.java` → Run As → Java Application

**Option 2 - Using Command Line:**
```bash
cd Bank-loan-master
mvn spring-boot:run
```

**Option 3 - Using IntelliJ IDEA:**
1. Open → Select `Bank-loan-master` folder
2. Wait for Maven import
3. Click Run button or press Shift+F10

---

## 🌐 Access the Application

**Open Browser:** http://localhost:8080

### ✅ Default Admin Login (Auto-Created)
- **Username:** `admin`
- **Password:** `admin123`

### ✅ Customer Registration
1. Click "Customer" tab on login page
2. Click "Sign up" link
3. Fill the registration form
4. Click "Register"
5. Login with your email and password

---

## 🎯 First Time Setup Flow

### Step 1: Login as Admin
- Username: `admin`
- Password: `admin123`

### Step 2: Create Loan Products
Navigate to: **Loan Products → Add New Product**

**Example Products:**

| Product Name  | Interest Rate | Min Amount | Max Amount   | Tenure (months) |
|---------------|---------------|------------|--------------|-----------------|
| Home Loan     | 8.5           | 500000     | 10000000     | 240             |
| Personal Loan | 10.5          | 50000      | 500000       | 60              |
| Vehicle Loan  | 9.0           | 100000     | 2000000      | 84              |

### Step 3: Register as Customer
1. Logout from admin
2. Click "Customer" tab → "Sign up"
3. Fill registration form
4. Login with your email

### Step 4: Verify Customer KYC (Admin)
1. Login as admin
2. Navigate to: **Customers**
3. Click "Verify KYC" for the registered customer

### Step 5: Apply for Loan (Customer)
1. Login as customer
2. Navigate to: **Apply Loans**
3. Select loan product
4. Enter loan amount
5. Submit application

### Step 6: Approve Loan (Admin)
1. Login as admin
2. Navigate to: **Applications**
3. Click "Approve" for pending application
4. EMI schedule is auto-generated

### Step 7: View Repayments (Customer)
1. Login as customer
2. Navigate to: **Repayments** or **View Loans → View Repayments**
3. See monthly EMI schedule
4. Click "Make a Payment" to pay EMI

---

## 🔧 Troubleshooting

### ❌ Problem: "Port 8080 already in use"
**Solution:** Change port in `application.properties`
```properties
server.port=8081
```
Access: http://localhost:8081

---

### ❌ Problem: "Cannot connect to database"
**Solutions:**
1. Check MySQL is running:
   - Windows: Services → MySQL80 → Start
   - Mac: `brew services start mysql`
   - Linux: `sudo service mysql start`

2. Verify database exists:
   ```sql
   SHOW DATABASES;
   ```
   If `bank_loan_db` is missing, create it:
   ```sql
   CREATE DATABASE bank_loan_db;
   ```

3. Check password in `application.properties` matches your MySQL password

---

### ❌ Problem: "Build failed" or Maven errors
**Solutions:**
1. Check internet connection (Maven downloads libraries)
2. In STS: Right-click project → Maven → Update Project → Check "Force Update"
3. Delete `.m2` folder and rebuild:
   - Windows: `C:\Users\YourName\.m2`
   - Mac/Linux: `~/.m2`

---

### ❌ Problem: Admin login not working
**Solution:** 
The admin user is auto-created on first startup. Check console logs for:
```
✅ Default admin user created successfully!
   Username: admin
   Password: admin123
```

If not created, manually insert:
```sql
USE bank_loan_db;
INSERT INTO admin (username, password, role) 
VALUES ('admin', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'ADMIN');
```
(Password is BCrypt encoded "admin123")

---

### ❌ Problem: Images not showing
**Solution:** 
Ensure these files exist in `src/main/resources/static/assets/`:
- Bank-login-bg.jpg
- slide1.png, slide2.png, slide3.png
- home_loan_card.png, personal_loan_card.png, vechile_loan_card.png

---

### ❌ Problem: Customer registration fails
**Solutions:**
1. Check console for error messages
2. Ensure all fields are filled
3. Use unique email (not already registered)
4. Password must match confirm password

---

## 📊 System Requirements

- **OS:** Windows 10/11, macOS, Linux
- **RAM:** 4GB minimum (8GB recommended)
- **Disk:** 500MB for application + dependencies
- **Java:** JDK 17 or higher
- **Database:** MySQL 8.0 or higher
- **Browser:** Chrome, Firefox, Edge, Safari (latest versions)

---

## 📱 Application Features

### Admin Features
✅ Dashboard with statistics  
✅ Customer management  
✅ KYC verification  
✅ Loan product management  
✅ Loan approval/rejection  
✅ Repayment tracking  
✅ Reports & analytics  

### Customer Features
✅ Self-registration  
✅ Loan application  
✅ View loan status  
✅ Repayment schedule  
✅ EMI payment  
✅ Loan report generation  

---

## 🎓 Technology Stack

- **Backend:** Java 25, Spring Boot 3.2.0
- **Security:** Spring Security with BCrypt
- **Database:** MySQL 8
- **ORM:** Spring Data JPA (Hibernate)
- **Frontend:** Thymeleaf, Bootstrap 5.3.3
- **Build:** Maven

---

## 📞 Quick Help

**Verify Installation:**
```bash
# Check Java
java -version
# Should show: java version "25.x.x"

# Check Maven
mvn -version
# Should show: Apache Maven 3.x.x

# Check MySQL
mysql --version
# Should show: mysql Ver 8.x.x
```

**Check Application Status:**
- Console should show: `Started BankLoanApplication in X seconds`
- No red error messages
- Port 8080 is listening

**Test Database Connection:**
```sql
mysql -u root -p
USE bank_loan_db;
SHOW TABLES;
```
Should show: admin, customer, loan_product, loan_application, repayment

---

## 🎉 Success Checklist

- [ ] Java 25 installed
- [ ] MySQL 8 installed and running
- [ ] Database `bank_loan_db` created
- [ ] Password configured in `application.properties`
- [ ] Application started successfully
- [ ] Can access http://localhost:8080
- [ ] Admin login works (admin/admin123)
- [ ] Customer registration works
- [ ] Can create loan products
- [ ] Can apply for loans

---

**Version:** 1.0.0  
**Status:** Production Ready ✅  
**Default Admin:** admin / admin123

**🎊 Enjoy using the Bank Loan Management System!**
