# Bank Loan Management System - API Documentation

## Base URL
```
http://localhost:8080/api
```

## Authentication
Currently, the API does not require authentication. In a production environment, you should implement proper authentication and authorization.

## API Endpoints

### 1. Customer Management

#### Register Customer
- **POST** `/customers`
- **Description**: Register a new customer
- **Request Body**:
```json
{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "address": "123 Main Street, City, State"
}
```
- **Response** (201 Created):
```json
{
  "customerId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "address": "123 Main Street, City, State",
  "kycStatus": "PENDING",
  "createdAt": "2024-01-15T10:30:00"
}
```

#### Get Customer Details
- **GET** `/customers/{customerId}`
- **Description**: Get customer details by ID
- **Response** (200 OK):
```json
{
  "customerId": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "address": "123 Main Street, City, State",
  "kycStatus": "VERIFIED",
  "createdAt": "2024-01-15T10:30:00"
}
```

#### Update Customer Profile
- **PUT** `/customers/{customerId}`
- **Description**: Update customer profile
- **Request Body**: Same as register customer
- **Response** (200 OK): Same as get customer details

#### Update KYC Status
- **PUT** `/customers/{customerId}/kyc-status?kycStatus=VERIFIED`
- **Description**: Update customer KYC status
- **Query Parameters**:
  - `kycStatus`: PENDING or VERIFIED
- **Response** (200 OK): Customer details with updated KYC status

#### Get All Customers
- **GET** `/customers`
- **Description**: Get all customers
- **Response** (200 OK): Array of customer objects

#### Get Customers by KYC Status
- **GET** `/customers/kyc-status/{kycStatus}`
- **Description**: Get customers filtered by KYC status
- **Path Parameters**:
  - `kycStatus`: PENDING or VERIFIED
- **Response** (200 OK): Array of customer objects

### 2. Loan Product Management

#### Add Loan Product
- **POST** `/loan-products`
- **Description**: Add a new loan product
- **Request Body**:
```json
{
  "productName": "Personal Loan",
  "interestRate": 12.5,
  "minAmount": 10000.00,
  "maxAmount": 500000.00,
  "tenure": 24
}
```
- **Response** (201 Created):
```json
{
  "loanProductId": 1,
  "productName": "Personal Loan",
  "interestRate": 12.5,
  "minAmount": 10000.00,
  "maxAmount": 500000.00,
  "tenure": 24,
  "createdAt": "2024-01-15T10:30:00"
}
```

#### Update Loan Product
- **PUT** `/loan-products/{productId}`
- **Description**: Update loan product details
- **Request Body**: Same as add loan product
- **Response** (200 OK): Updated loan product details

#### Get Loan Product Details
- **GET** `/loan-products/{productId}`
- **Description**: Get loan product details by ID
- **Response** (200 OK): Loan product details

#### Get All Loan Products
- **GET** `/loan-products`
- **Description**: Get all loan products
- **Response** (200 OK): Array of loan product objects

#### Get Eligible Products
- **GET** `/loan-products/eligible?loanAmount=50000`
- **Description**: Get loan products eligible for the specified amount
- **Query Parameters**:
  - `loanAmount`: The loan amount to check eligibility for
- **Response** (200 OK): Array of eligible loan products

### 3. Loan Application Management

#### Apply for Loan
- **POST** `/loan-applications`
- **Description**: Apply for a loan
- **Request Body**:
```json
{
  "customerId": 1,
  "loanProductId": 1,
  "loanAmount": 50000.00
}
```
- **Response** (201 Created):
```json
{
  "applicationId": 1,
  "customerId": 1,
  "customerName": "John Doe",
  "loanProductId": 1,
  "productName": "Personal Loan",
  "loanAmount": 50000.00,
  "applicationDate": "2024-01-15",
  "approvalStatus": "PENDING"
}
```

#### Get Application Status
- **GET** `/loan-applications/{applicationId}`
- **Description**: Get loan application status
- **Response** (200 OK): Loan application details

#### Process Loan Application
- **PUT** `/loan-applications/{applicationId}/process?status=APPROVED`
- **Description**: Process loan application (approve/reject)
- **Query Parameters**:
  - `status`: PENDING, APPROVED, or REJECTED
- **Response** (200 OK): Updated loan application details

#### Get Applications by Customer
- **GET** `/loan-applications/customer/{customerId}`
- **Description**: Get all loan applications for a customer
- **Response** (200 OK): Array of loan application objects

#### Get Applications by Status
- **GET** `/loan-applications/status/{status}`
- **Description**: Get loan applications filtered by status
- **Path Parameters**:
  - `status`: PENDING, APPROVED, or REJECTED
- **Response** (200 OK): Array of loan application objects

### 4. Repayment Management

#### Get Repayment Schedule
- **GET** `/repayments/schedule/{applicationId}`
- **Description**: Get repayment schedule for a loan application
- **Response** (200 OK):
```json
[
  {
    "repaymentId": 1,
    "applicationId": 1,
    "dueDate": "2024-02-15",
    "amountDue": 2500.00,
    "paymentDate": null,
    "paymentStatus": "PENDING"
  },
  {
    "repaymentId": 2,
    "applicationId": 1,
    "dueDate": "2024-03-15",
    "amountDue": 2500.00,
    "paymentDate": null,
    "paymentStatus": "PENDING"
  }
]
```

#### Make Payment
- **POST** `/repayments/payment`
- **Description**: Record a payment
- **Request Body**:
```json
{
  "repaymentId": 1,
  "paymentAmount": 2500.00,
  "paymentDate": "2024-02-15"
}
```
- **Response** (200 OK):
```json
{
  "repaymentId": 1,
  "applicationId": 1,
  "dueDate": "2024-02-15",
  "amountDue": 2500.00,
  "paymentDate": "2024-02-15",
  "paymentStatus": "COMPLETED"
}
```

#### Get Outstanding Balance
- **GET** `/repayments/outstanding/{applicationId}`
- **Description**: Get outstanding balance for a loan application
- **Response** (200 OK):
```json
47500.00
```

#### Get Pending Repayments
- **GET** `/repayments/pending/{applicationId}`
- **Description**: Get pending repayments for a loan application
- **Response** (200 OK): Array of pending repayment objects

#### Get Completed Repayments
- **GET** `/repayments/completed/{applicationId}`
- **Description**: Get completed repayments for a loan application
- **Response** (200 OK): Array of completed repayment objects

### 5. Reporting

#### Generate Loan Report
- **GET** `/reports/loans`
- **Description**: Generate comprehensive loan statistics report
- **Response** (200 OK):
```json
{
  "totalApplications": 150,
  "applicationsByStatus": {
    "PENDING": 25,
    "APPROVED": 100,
    "REJECTED": 25
  },
  "totalApprovedAmount": 5000000.00,
  "averageLoanAmount": 33333.33
}
```

#### Generate Repayment Report
- **GET** `/reports/repayments`
- **Description**: Generate repayment statistics report
- **Response** (200 OK):
```json
{
  "totalRepayments": 500,
  "repaymentsByStatus": {
    "PENDING": 200,
    "COMPLETED": 300
  },
  "totalAmountCollected": 1500000.00,
  "overduePaymentsCount": 15,
  "overdueAmount": 37500.00
}
```

#### Generate Outstanding Loans Report
- **GET** `/reports/outstanding`
- **Description**: Generate outstanding loans report
- **Response** (200 OK):
```json
{
  "totalOutstandingLoans": 80,
  "totalOutstandingAmount": 2000000.00,
  "outstandingByCustomer": {
    "John Doe": 50000.00,
    "Jane Smith": 75000.00
  }
}
```

#### Generate Customer Portfolio Report
- **GET** `/reports/customer-portfolio/{customerId}`
- **Description**: Generate customer-specific portfolio report
- **Response** (200 OK):
```json
{
  "customerId": 1,
  "customerName": "John Doe",
  "kycStatus": "VERIFIED",
  "totalApplications": 3,
  "applicationsByStatus": {
    "PENDING": 0,
    "APPROVED": 2,
    "REJECTED": 1
  },
  "totalAppliedAmount": 150000.00,
  "totalOutstandingBalance": 75000.00
}
```

## Error Responses

### 400 Bad Request
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/customers"
}
```

### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "Not Found",
  "message": "Customer not found with ID: 999",
  "path": "/api/customers/999"
}
```

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred",
  "path": "/api/customers"
}
```

## Validation Rules

### Customer
- `name`: Required, max 100 characters
- `email`: Required, valid email format, max 100 characters, unique
- `phone`: Required, 10-15 digits
- `address`: Optional, max 500 characters

### Loan Product
- `productName`: Required, max 50 characters
- `interestRate`: Required, non-negative decimal
- `minAmount`: Required, non-negative decimal
- `maxAmount`: Required, non-negative decimal, must be greater than minAmount
- `tenure`: Required, minimum 1 month

### Loan Application
- `customerId`: Required, must exist
- `loanProductId`: Required, must exist
- `loanAmount`: Required, non-negative decimal, must be within product limits

### Payment
- `repaymentId`: Required, must exist
- `paymentAmount`: Required, non-negative decimal, must equal due amount
- `paymentDate`: Required, valid date

## Business Rules

1. **KYC Verification**: Customers must have VERIFIED KYC status to apply for loans
2. **Loan Amount Validation**: Loan amount must be within the product's min/max limits
3. **Pending Applications**: Customers cannot have multiple pending loan applications
4. **Repayment Schedule**: Automatically generated when loan is approved
5. **Payment Validation**: Payment amount must exactly match the due amount
6. **Status Transitions**: Applications can only be processed from PENDING status
