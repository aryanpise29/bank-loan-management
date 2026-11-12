# Bank Loan Management System - Module Workflows

This document explains how each of the 5 core modules works, their business logic, and the flow of operations.

## 1. Customer Management Module

### Purpose
Handles customer registration, account details, and KYC (Know Your Customer) verification.

### Workflow

#### Customer Registration Process
1. **Customer Registration**
   - Customer provides personal information (name, email, phone, address)
   - System validates email uniqueness
   - Customer is created with PENDING KYC status
   - Returns customer details with generated customerId

2. **KYC Verification Process**
   - Administrator reviews customer information
   - Updates KYC status from PENDING to VERIFIED
   - Only VERIFIED customers can apply for loans

3. **Profile Updates**
   - Customers can update their profile information
   - Email uniqueness is validated if changed
   - KYC status remains unchanged during profile updates

### Business Logic
- **Email Uniqueness**: Each customer must have a unique email address
- **KYC Requirement**: Only customers with VERIFIED KYC status can apply for loans
- **Data Validation**: All customer data is validated according to business rules

### Key Operations
- `registerCustomer()`: Creates new customer with PENDING KYC
- `updateKycStatus()`: Updates KYC status (PENDING ↔ VERIFIED)
- `updateCustomerProfile()`: Updates customer information
- `getCustomerDetails()`: Retrieves customer information

---

## 2. Loan Product Management Module

### Purpose
Allows administrators to create and manage loan product details including interest rates, amounts, and tenures.

### Workflow

#### Product Creation Process
1. **Product Definition**
   - Administrator defines loan product parameters
   - Sets interest rate, minimum/maximum amounts, and tenure
   - System validates that max amount > min amount
   - Product is saved and available for loan applications

2. **Product Updates**
   - Existing products can be updated
   - Same validation rules apply
   - Changes affect future loan applications only

### Business Logic
- **Amount Validation**: Maximum amount must be greater than minimum amount
- **Interest Rate**: Stored as decimal (e.g., 12.5 for 12.5%)
- **Tenure**: Specified in months
- **Product Availability**: All active products are available for applications

### Key Operations
- `addLoanProduct()`: Creates new loan product
- `updateLoanProduct()`: Updates existing product
- `getEligibleProducts()`: Finds products suitable for loan amount
- `getLoanProductDetails()`: Retrieves product information

---

## 3. Loan Application Management Module

### Purpose
Facilitates loan application and approval workflows from submission to final decision.

### Workflow

#### Loan Application Process
1. **Application Submission**
   - Customer selects loan product and specifies amount
   - System validates customer KYC status (must be VERIFIED)
   - System validates loan amount is within product limits
   - System checks for existing pending applications
   - Application is created with PENDING status

2. **Application Processing**
   - Administrator reviews application
   - Can approve or reject the application
   - Status changes from PENDING to APPROVED/REJECTED
   - If approved, repayment schedule is automatically generated

### Business Logic
- **KYC Requirement**: Only VERIFIED customers can apply
- **Amount Validation**: Loan amount must be within product min/max limits
- **Single Pending Application**: Customers cannot have multiple pending applications
- **Automatic Schedule Generation**: Repayment schedule created upon approval
- **Status Management**: Applications follow PENDING → APPROVED/REJECTED flow

### Key Operations
- `applyForLoan()`: Creates new loan application
- `processLoanApplication()`: Approves or rejects application
- `getApplicationStatus()`: Retrieves application details
- `getApplicationsByCustomer()`: Gets all applications for a customer

---

## 4. Repayment Management Module

### Purpose
Tracks loan repayment schedules, processes payments, and calculates outstanding balances.

### Workflow

#### Repayment Schedule Generation
1. **Automatic Generation**
   - Triggered when loan application is approved
   - Uses EMI (Equated Monthly Installment) formula
   - Calculates monthly payment based on:
     - Principal amount
     - Interest rate
     - Tenure in months
   - Creates monthly repayment entries

2. **Payment Processing**
   - Customer makes payment for specific repayment
   - System validates payment amount matches due amount
   - Updates repayment status to COMPLETED
   - Records payment date

3. **Balance Tracking**
   - System calculates outstanding balance
   - Considers all pending repayments
   - Updates in real-time as payments are made

### Business Logic
- **EMI Calculation**: Uses standard EMI formula for monthly payments
- **Payment Validation**: Payment amount must exactly match due amount
- **Status Tracking**: Repayments are PENDING until payment is made
- **Balance Calculation**: Sum of all pending repayment amounts
- **Schedule Integrity**: Repayment schedule cannot be modified once created

### Key Operations
- `generateRepaymentSchedule()`: Creates monthly repayment schedule
- `makePayment()`: Records payment and updates status
- `getOutstandingBalance()`: Calculates remaining balance
- `getRepaymentSchedule()`: Retrieves complete schedule

---

## 5. Reporting and Auditing Module

### Purpose
Generates insightful reports for administrators and customers, providing transparency and aiding decision-making.

### Workflow

#### Report Generation Process
1. **Data Aggregation**
   - System queries relevant data from all modules
   - Calculates statistics and metrics
   - Formats data for presentation

2. **Report Types**
   - **Loan Reports**: Application statistics, approval rates, amounts
   - **Repayment Reports**: Payment status, collections, overdue amounts
   - **Outstanding Reports**: Current loan balances, customer portfolios
   - **Customer Portfolios**: Individual customer loan history and status

### Business Logic
- **Real-time Data**: Reports reflect current system state
- **Comprehensive Coverage**: Includes all relevant business metrics
- **Customer-specific Views**: Individual portfolio reports
- **Administrative Insights**: High-level statistics for management

### Key Operations
- `generateLoanReport()`: Overall loan statistics
- `generateRepaymentReport()`: Payment and collection statistics
- `generateOutstandingLoansReport()`: Current outstanding balances
- `generateCustomerPortfolioReport()`: Individual customer details

---

## Complete End-to-End Workflow

### Typical Loan Lifecycle

1. **Customer Onboarding**
   ```
   Customer Registration → KYC Verification → Profile Complete
   ```

2. **Loan Application**
   ```
   Product Selection → Application Submission → Administrative Review → Approval/Rejection
   ```

3. **Loan Disbursement** (if approved)
   ```
   Approval → Repayment Schedule Generation → Loan Active
   ```

4. **Repayment Management**
   ```
   Monthly Payments → Balance Tracking → Loan Completion
   ```

5. **Reporting and Monitoring**
   ```
   Continuous Monitoring → Report Generation → Business Insights
   ```

### Key Integration Points

- **Customer ↔ Loan Application**: KYC status validation
- **Loan Product ↔ Loan Application**: Amount and terms validation
- **Loan Application ↔ Repayment**: Automatic schedule generation
- **All Modules ↔ Reporting**: Data aggregation for insights

### Error Handling and Validation

- **Input Validation**: All inputs validated at API level
- **Business Rule Validation**: Custom validation in service layer
- **Exception Handling**: Comprehensive error responses
- **Data Integrity**: Database constraints and relationships

This modular design ensures each component has clear responsibilities while maintaining seamless integration across the entire loan management system.
