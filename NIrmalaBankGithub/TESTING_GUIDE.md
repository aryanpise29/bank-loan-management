# 🧪 Testing Guide - Bank Loan Management System

## Complete Testing Workflow

---

## Prerequisites
- Application running on http://localhost:8080
- MySQL database running
- Admin credentials: admin / admin123

---

## Test Scenario 1: Admin Login ✅

### Steps:
1. Open browser: http://localhost:8080
2. You should be redirected to login page
3. Enter credentials:
   - Username: `admin`
   - Password: `admin123`
4. Click "Login"

### Expected Result:
✅ Redirected to Dashboard  
✅ See navigation menu  
✅ See statistics (all zeros initially)

---

## Test Scenario 2: Create Loan Products ✅

### Test Case 2.1: Personal Loan
1. Click "Loan Products" in navigation
2. Click "Add New Product"
3. Fill in:
   - Product Name: `Personal Loan`
   - Interest Rate: `10.5`
   - Min Amount: `10000`
   - Max Amount: `500000`
   - Tenure: `12`
4. Click "Save Product"

### Expected Result:
✅ Redirected to loan products page  
✅ Personal Loan appears in table

### Test Case 2.2: Home Loan
Repeat with:
- Product Name: `Home Loan`
- Interest Rate: `8.5`
- Min Amount: `500000`
- Max Amount: `5000000`
- Tenure: `240`

### Test Case 2.3: Vehicle Loan
Repeat with:
- Product Name: `Vehicle Loan`
- Interest Rate: `12.0`
- Min Amount: `50000`
- Max Amount: `1000000`
- Tenure: `60`

---

## Test Scenario 3: Register Customers ✅

### Test Case 3.1: Customer 1
1. Click "Customers" in navigation
2. Click "Add New Customer"
3. Fill in:
   - Full Name: `John Doe`
   - Email: `john@example.com`
   - Phone: `9876543210`
   - Address: `123 Main Street, New York`
4. Click "Register Customer"

### Expected Result:
✅ Redirected to customers page  
✅ John Doe appears with KYC Status: PENDING

### Test Case 3.2: Customer 2
Repeat with:
- Name: `Jane Smith`
- Email: `jane@example.com`
- Phone: `9876543211`
- Address: `456 Oak Avenue, Boston`

### Test Case 3.3: Customer 3
Repeat with:
- Name: `Robert Johnson`
- Email: `robert@example.com`
- Phone: `9876543212`
- Address: `789 Pine Road, Chicago`

---

## Test Scenario 4: Verify KYC ✅

### Steps:
1. Go to "Customers" page
2. For each customer, click "Verify KYC" button
3. Observe status change

### Expected Result:
✅ KYC Status changes from PENDING to VERIFIED  
✅ "Verify KYC" button disappears  
✅ Shows "✓ Verified" text

---

## Test Scenario 5: Apply for Loans (via API) ✅

### Test Case 5.1: John's Personal Loan
Open terminal/command prompt:

```bash
curl -X POST http://localhost:8080/api/loan-applications \
  -H "Content-Type: application/json" \
  -d "{\"customerId\": 1, \"loanProductId\": 1, \"loanAmount\": 100000}"
```

### Test Case 5.2: Jane's Home Loan
```bash
curl -X POST http://localhost:8080/api/loan-applications \
  -H "Content-Type: application/json" \
  -d "{\"customerId\": 2, \"loanProductId\": 2, \"loanAmount\": 2000000}"
```

### Test Case 5.3: Robert's Vehicle Loan
```bash
curl -X POST http://localhost:8080/api/loan-applications \
  -H "Content-Type: application/json" \
  -d "{\"customerId\": 3, \"loanProductId\": 3, \"loanAmount\": 500000}"
```

### Expected Result:
✅ Each request returns JSON with application details  
✅ Status: PENDING

---

## Test Scenario 6: View Pending Applications ✅

### Steps:
1. Click "Applications" in navigation
2. View pending applications

### Expected Result:
✅ See 3 pending applications  
✅ Each shows customer name, product, amount  
✅ "Approve" and "Reject" buttons visible

---

## Test Scenario 7: Approve Loans ✅

### Test Case 7.1: Approve John's Loan
1. In Applications page
2. Find John Doe's application
3. Click "Approve" button

### Expected Result:
✅ Application disappears from pending list  
✅ Dashboard shows 1 approved loan  
✅ EMI schedule automatically generated

### Test Case 7.2: Approve Jane's Loan
Repeat for Jane Smith's application

### Test Case 7.3: Reject Robert's Loan
1. Find Robert Johnson's application
2. Click "Reject" button

### Expected Result:
✅ Application disappears from pending list  
✅ Dashboard shows 1 rejected loan

---

## Test Scenario 8: View All Applications ✅

### Steps:
1. Click "All Applications" in navigation
2. Review all sections

### Expected Result:
✅ Pending section: Empty  
✅ Approved section: 2 applications (John, Jane)  
✅ Rejected section: 1 application (Robert)  
✅ "View Repayments" link for approved loans

---

## Test Scenario 9: View Repayment Schedule ✅

### Test Case 9.1: John's Repayments
1. In "All Applications" page
2. Click "View Repayments" for John's loan
3. Review EMI schedule

### Expected Result:
✅ Shows 12 EMIs (tenure = 12 months)  
✅ Each EMI shows due date, amount  
✅ All status: PENDING  
✅ Outstanding balance shown  
✅ EMI amount calculated correctly

### Test Case 9.2: Jane's Repayments
Repeat for Jane's loan

### Expected Result:
✅ Shows 240 EMIs (tenure = 240 months)  
✅ EMI amounts calculated for home loan

---

## Test Scenario 10: Dashboard Verification ✅

### Steps:
1. Click "Dashboard" in navigation
2. Review all statistics

### Expected Result:
✅ Total Applications: 3  
✅ Approved Loans: 2  
✅ Pending Applications: 0  
✅ Rejected Applications: 1  
✅ Total Approved Amount: ₹21,00,000 (1,00,000 + 20,00,000)  
✅ Average Loan Amount: ₹7,00,000  
✅ Total Repayments: 252 (12 + 240)  
✅ Pending Payments: 252  
✅ Completed Payments: 0

---

## Test Scenario 11: Reports ✅

### Steps:
1. Click "Reports" in navigation
2. Review all report sections

### Expected Result:
✅ Loan Statistics section populated  
✅ Repayment Statistics section populated  
✅ Outstanding Loans section populated  
✅ All numbers match dashboard

---

## Test Scenario 12: API Testing ✅

### Test Case 12.1: Get All Customers
```bash
curl http://localhost:8080/api/customers
```

### Expected Result:
✅ Returns JSON array with 3 customers

### Test Case 12.2: Get Customer by ID
```bash
curl http://localhost:8080/api/customers/1
```

### Expected Result:
✅ Returns John Doe's details

### Test Case 12.3: Get All Loan Products
```bash
curl http://localhost:8080/api/loan-products
```

### Expected Result:
✅ Returns 3 loan products

### Test Case 12.4: Get Repayment Schedule
```bash
curl http://localhost:8080/api/repayments/schedule/1
```

### Expected Result:
✅ Returns 12 EMI entries for John's loan

### Test Case 12.5: Get Outstanding Balance
```bash
curl http://localhost:8080/api/repayments/outstanding/1
```

### Expected Result:
✅ Returns total outstanding amount

### Test Case 12.6: Generate Loan Report
```bash
curl http://localhost:8080/api/reports/loans
```

### Expected Result:
✅ Returns comprehensive loan statistics

---

## Test Scenario 13: Negative Testing ✅

### Test Case 13.1: Apply Loan Without KYC
1. Register new customer (don't verify KYC)
2. Try to apply for loan via API

### Expected Result:
✅ Error: "Customer KYC must be verified"

### Test Case 13.2: Apply Loan with Invalid Amount
```bash
curl -X POST http://localhost:8080/api/loan-applications \
  -H "Content-Type: application/json" \
  -d "{\"customerId\": 1, \"loanProductId\": 1, \"loanAmount\": 5000}"
```

### Expected Result:
✅ Error: "Loan amount must be between min and max"

### Test Case 13.3: Duplicate Email
1. Try to register customer with existing email

### Expected Result:
✅ Error: "Customer with email already exists"

### Test Case 13.4: Invalid Login
1. Try to login with wrong password

### Expected Result:
✅ Error message: "Invalid username or password"

---

## Test Scenario 14: Logout ✅

### Steps:
1. Click "Logout" button in navigation
2. Observe behavior

### Expected Result:
✅ Redirected to login page  
✅ Success message: "Logged out successfully"  
✅ Cannot access dashboard without login

---

## Performance Testing

### Load Test
1. Create 10 customers
2. Create 5 loan products
3. Submit 20 loan applications
4. Approve all applications
5. Check dashboard performance

### Expected Result:
✅ All operations complete successfully  
✅ Dashboard loads within 2 seconds  
✅ Reports generate within 3 seconds

---

## Browser Compatibility Testing

### Test on:
- [ ] Google Chrome
- [ ] Mozilla Firefox
- [ ] Microsoft Edge
- [ ] Safari (if on Mac)

### Expected Result:
✅ UI displays correctly on all browsers  
✅ All functionality works

---

## Mobile Responsiveness (Optional)

### Test on:
- [ ] Mobile browser (Chrome/Safari)
- [ ] Tablet

### Expected Result:
✅ Pages are readable  
✅ Forms are usable  
✅ Tables scroll horizontally if needed

---

## Database Verification

### Steps:
```sql
-- Connect to MySQL
mysql -u root -p

USE bank_loan_db;

-- Check tables
SHOW TABLES;

-- Check data
SELECT * FROM admin;
SELECT * FROM customer;
SELECT * FROM loan_product;
SELECT * FROM loan_application;
SELECT * FROM repayment;
```

### Expected Result:
✅ All 5 tables exist  
✅ Data matches UI  
✅ Relationships maintained

---

## Test Results Checklist

### Functional Testing
- [ ] Admin login works
- [ ] Customer registration works
- [ ] KYC verification works
- [ ] Loan product creation works
- [ ] Loan application works
- [ ] Loan approval works
- [ ] Loan rejection works
- [ ] EMI calculation correct
- [ ] Repayment schedule generated
- [ ] Dashboard shows correct data
- [ ] Reports generate correctly
- [ ] All APIs work

### Security Testing
- [ ] Cannot access pages without login
- [ ] Password is encrypted
- [ ] Session management works
- [ ] Logout works

### UI Testing
- [ ] All pages load correctly
- [ ] Forms validate input
- [ ] Buttons work
- [ ] Navigation works
- [ ] Tables display data
- [ ] Styling is consistent

### API Testing
- [ ] All GET endpoints work
- [ ] All POST endpoints work
- [ ] All PUT endpoints work
- [ ] Error handling works
- [ ] JSON responses correct

---

## Bug Reporting Template

If you find any issues:

```
**Bug Title:** [Brief description]

**Steps to Reproduce:**
1. Step 1
2. Step 2
3. Step 3

**Expected Result:**
[What should happen]

**Actual Result:**
[What actually happened]

**Screenshots:**
[If applicable]

**Environment:**
- OS: [Windows/Linux/Mac]
- Browser: [Chrome/Firefox/etc]
- Java Version: [17]
- MySQL Version: [8.0]
```

---

## Test Summary Report Template

```
**Test Date:** [Date]
**Tester:** [Name]
**Version:** 1.0.0

**Test Results:**
- Total Test Cases: 50+
- Passed: __
- Failed: __
- Blocked: __
- Not Tested: __

**Pass Rate:** __%

**Critical Issues:** [None/List]
**Major Issues:** [None/List]
**Minor Issues:** [None/List]

**Overall Status:** [PASS/FAIL]
**Ready for Production:** [YES/NO]
```

---

## Automated Testing (Future)

### Unit Tests
```java
@Test
void testRegisterCustomer() {
    // Test customer registration
}

@Test
void testApplyForLoan() {
    // Test loan application
}
```

### Integration Tests
```java
@Test
void testCompleteLoanWorkflow() {
    // Test end-to-end workflow
}
```

---

## Testing Complete! ✅

Once all test scenarios pass, the application is ready for:
- ✅ Demonstration
- ✅ Client review
- ✅ Production deployment
- ✅ User training

---

**Happy Testing! 🧪**
