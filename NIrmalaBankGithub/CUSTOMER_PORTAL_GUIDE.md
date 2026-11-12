# 👥 Customer Portal Guide

## Overview

The Bank Loan Management System now includes a **Customer Self-Service Portal** where customers can:
- Register their own accounts
- Login securely
- Apply for loans online
- Track loan applications
- View EMI schedules
- Monitor repayment status

---

## 🚀 Customer Portal Features

### ✅ Customer Registration
- Self-registration with email and password
- Automatic account creation
- KYC pending by default (requires admin verification)

### ✅ Customer Login
- Secure authentication
- Email-based login
- Password encryption

### ✅ Customer Dashboard
- Welcome screen with customer details
- KYC status display
- Recent loan applications
- Quick action buttons

### ✅ Apply for Loan
- Browse available loan products
- View product details (interest rate, amount range, tenure)
- Submit loan application online
- Real-time validation

### ✅ My Loans
- View all loan applications
- Categorized by status (Pending, Approved, Rejected)
- Track application progress

### ✅ EMI Schedule
- View complete repayment schedule
- Outstanding balance
- Payment status for each EMI
- Due dates and amounts

---

## 📋 Customer Workflow

### Step 1: Customer Registration
1. Go to: **http://localhost:8080/customer-register**
2. Fill in registration form:
   - Full Name
   - Email Address
   - Password (minimum 6 characters)
   - Phone Number (10-15 digits)
   - Address (optional)
3. Click "Create Account"
4. Redirected to login page

### Step 2: Wait for KYC Verification
- Admin must verify KYC before customer can apply for loans
- Customer can login but cannot submit loan applications
- Dashboard shows KYC status

### Step 3: Customer Login
1. Go to: **http://localhost:8080/customer-login**
2. Enter email and password
3. Click "Login"
4. Redirected to customer dashboard

### Step 4: Apply for Loan
1. Click "Apply for New Loan" on dashboard
2. Select loan product from dropdown
3. View product details (auto-displayed)
4. Enter desired loan amount
5. Click "Submit Application"
6. Application sent for admin approval

### Step 5: Track Application
1. Go to "My Loans" page
2. View application status:
   - **PENDING**: Waiting for admin approval
   - **APPROVED**: Loan approved, EMI schedule generated
   - **REJECTED**: Application rejected by admin

### Step 6: View EMI Schedule (After Approval)
1. Go to "My Loans"
2. Click "View EMI Schedule" for approved loans
3. See complete repayment schedule
4. Monitor outstanding balance

---

## 🔐 Security Features

### Customer Authentication
- **Email-based login**: Unique email per customer
- **Password encryption**: BCrypt hashing
- **Session management**: Secure sessions
- **Role-based access**: CUSTOMER role assigned

### Access Control
- Customers can only view their own data
- Cannot access admin pages
- Cannot modify other customers' information
- Automatic logout on session expiry

---

## 🎯 Admin Responsibilities

### KYC Verification
1. Admin logs in to admin portal
2. Goes to "Customers" page
3. Sees all registered customers
4. Clicks "Verify KYC" for each customer
5. Customer can now apply for loans

### Loan Approval
1. Customer submits loan application
2. Admin sees application in "Applications" page
3. Admin reviews and clicks "Approve" or "Reject"
4. If approved, EMI schedule auto-generated
5. Customer can view EMI schedule

---

## 📱 Customer Portal Pages

### 1. Customer Registration (`/customer-register`)
- Self-service registration form
- Email validation
- Password creation
- Phone number validation

### 2. Customer Login (`/customer-login`)
- Email and password login
- Link to registration
- Link to admin login

### 3. Customer Dashboard (`/customer/dashboard`)
- Welcome message
- Customer details
- KYC status badge
- Recent applications table
- Quick action buttons

### 4. Apply for Loan (`/customer/apply-loan`)
- Loan product dropdown
- Dynamic product information
- Amount input with validation
- KYC check before submission

### 5. My Loans (`/customer/my-loans`)
- Pending applications section
- Approved loans section
- Rejected applications section
- View EMI button for approved loans

### 6. EMI Schedule (`/customer/repayments/{id}`)
- Outstanding balance summary
- Complete EMI schedule table
- Payment status for each EMI
- Due dates and amounts

---

## 🧪 Testing Customer Portal

### Test Scenario 1: Customer Registration
```
1. Open: http://localhost:8080/customer-register
2. Fill form:
   - Name: John Customer
   - Email: john.customer@example.com
   - Password: password123
   - Phone: 9876543210
   - Address: 123 Customer Street
3. Click "Create Account"
4. Should redirect to login with success message
```

### Test Scenario 2: Customer Login
```
1. Open: http://localhost:8080/customer-login
2. Enter:
   - Email: john.customer@example.com
   - Password: password123
3. Click "Login"
4. Should redirect to customer dashboard
```

### Test Scenario 3: Apply for Loan (Before KYC)
```
1. Login as customer
2. Click "Apply for New Loan"
3. Should see warning: KYC not verified
4. Submit button should be disabled
```

### Test Scenario 4: Admin Verifies KYC
```
1. Logout from customer portal
2. Login to admin portal (admin/admin123)
3. Go to "Customers"
4. Find John Customer
5. Click "Verify KYC"
6. Status changes to VERIFIED
```

### Test Scenario 5: Apply for Loan (After KYC)
```
1. Login as customer again
2. Click "Apply for New Loan"
3. Select "Personal Loan"
4. Enter amount: 100000
5. Click "Submit Application"
6. Should redirect to dashboard with success message
```

### Test Scenario 6: Admin Approves Loan
```
1. Login to admin portal
2. Go to "Applications"
3. Find John's application
4. Click "Approve"
5. EMI schedule auto-generated
```

### Test Scenario 7: View EMI Schedule
```
1. Login as customer
2. Go to "My Loans"
3. Find approved loan
4. Click "View EMI Schedule"
5. Should see complete repayment schedule
```

---

## 🔄 Complete End-to-End Flow

```
Customer Side:
1. Register → /customer-register
2. Login → /customer-login
3. Dashboard → Shows KYC PENDING
4. Wait for admin verification

Admin Side:
5. Login → /login (admin/admin123)
6. Customers → Verify KYC

Customer Side:
7. Dashboard → Shows KYC VERIFIED
8. Apply for Loan → Select product, enter amount
9. My Loans → Shows PENDING status

Admin Side:
10. Applications → Approve loan
11. EMI schedule auto-generated

Customer Side:
12. My Loans → Shows APPROVED status
13. View EMI Schedule → See all EMIs
14. Monitor repayment progress
```

---

## 📊 Database Changes

### Customer Table Updated
```sql
ALTER TABLE customer 
ADD COLUMN password VARCHAR(255),
ADD COLUMN role VARCHAR(50) DEFAULT 'CUSTOMER';
```

- **password**: Encrypted password for login
- **role**: Always 'CUSTOMER' for customer accounts

---

## 🎨 UI Features

### Customer Portal Design
- **Modern gradient navbar**: Purple/blue gradient
- **Responsive layout**: Works on all screen sizes
- **Color-coded badges**: 
  - Yellow for PENDING
  - Green for APPROVED/VERIFIED
  - Red for REJECTED
- **Interactive forms**: Real-time validation
- **Dynamic content**: Product info updates on selection

### Navigation
- **Dashboard**: Overview and quick actions
- **Apply for Loan**: Loan application form
- **My Loans**: All applications by status
- **Logout**: Secure logout

---

## 🔗 URLs Summary

| Page | URL | Access |
|------|-----|--------|
| Admin Login | /login | Public |
| Customer Login | /customer-login | Public |
| Customer Register | /customer-register | Public |
| Customer Dashboard | /customer/dashboard | Customer only |
| Apply for Loan | /customer/apply-loan | Customer only |
| My Loans | /customer/my-loans | Customer only |
| EMI Schedule | /customer/repayments/{id} | Customer only |

---

## ⚠️ Important Notes

### KYC Requirement
- Customers **CANNOT** apply for loans without KYC verification
- Admin must verify KYC first
- Warning message shown on apply page if KYC pending

### Password Security
- Passwords are encrypted using BCrypt
- Minimum 6 characters required
- Never stored in plain text

### Email Uniqueness
- Each email can only register once
- Email is used as username for login
- Case-sensitive email matching

### Session Management
- Customers stay logged in until logout
- Separate sessions for admin and customer
- Automatic logout on browser close

---

## 🚀 Quick Start for Customers

### For New Customers:
1. Visit: http://localhost:8080/customer-register
2. Create account
3. Wait for admin to verify KYC
4. Login and apply for loans

### For Existing Customers:
1. Visit: http://localhost:8080/customer-login
2. Login with email and password
3. View dashboard
4. Apply for loans or check status

---

## 📞 Support

### Customer Issues:
- **Can't login**: Check email and password
- **Can't apply**: Check KYC status on dashboard
- **Application not showing**: Refresh page or check "My Loans"

### Admin Tasks:
- **Verify KYC**: Go to Customers page
- **Approve loans**: Go to Applications page
- **View customer details**: Click on customer in list

---

## ✅ Customer Portal Checklist

- [x] Customer registration page
- [x] Customer login page
- [x] Customer dashboard
- [x] Apply for loan page
- [x] My loans page
- [x] EMI schedule page
- [x] KYC verification check
- [x] Password encryption
- [x] Role-based access control
- [x] Secure authentication
- [x] Responsive design
- [x] User-friendly interface

---

## 🎉 Customer Portal Complete!

The customer portal is now **fully functional** and integrated with the admin portal!

**Customers can:**
✅ Register themselves  
✅ Login securely  
✅ Apply for loans online  
✅ Track applications  
✅ View EMI schedules  
✅ Monitor repayments  

**Admins can:**
✅ Verify customer KYC  
✅ Approve/reject loans  
✅ Manage all customers  
✅ View all applications  
✅ Generate reports  

---

**Version:** 1.0.0  
**Status:** ✅ Complete  
**Ready for:** Production Use
