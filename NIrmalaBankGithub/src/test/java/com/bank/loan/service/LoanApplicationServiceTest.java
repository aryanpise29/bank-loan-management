package com.bank.loan.service;

import com.bank.loan.dto.*;
import com.bank.loan.exception.InvalidLoanApplicationException;
import com.bank.loan.exception.LoanApplicationNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.model.LoanApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LoanApplicationServiceTest {

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanProductService loanProductService;

    private Long customerId;
    private Long productId;

    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john@example.com");
        customerRequest.setPhone("1234567890");
        customerRequest.setPassword("password123");

        CustomerResponse customer = customerService.registerCustomer(customerRequest);
        customerId = customer.getCustomerId();
        customerService.updateKycStatus(customerId, Customer.KycStatus.VERIFIED);

        LoanProductRequest productRequest = new LoanProductRequest();
        productRequest.setProductName("Personal Loan");
        productRequest.setInterestRate(new BigDecimal("10.5"));
        productRequest.setMinAmount(new BigDecimal("10000"));
        productRequest.setMaxAmount(new BigDecimal("500000"));

        LoanProductResponse product = loanProductService.addLoanProduct(productRequest);
        productId = product.getLoanProductId();
    }

    @Test
    void testApplyForLoan() {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        LoanApplicationResponse response = loanApplicationService.applyForLoan(request);

        assertNotNull(response);
        assertEquals(new BigDecimal("100000"), response.getLoanAmount());
        assertEquals(LoanApplication.ApprovalStatus.PENDING, response.getApprovalStatus());
    }

    @Test
    void testApplyForLoanUnverifiedKyc() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("Jane Doe");
        customerRequest.setEmail("jane@example.com");
        customerRequest.setPhone("1234567891");
        customerRequest.setPassword("password123");

        CustomerResponse customer = customerService.registerCustomer(customerRequest);

        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customer.getCustomerId());
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        assertThrows(InvalidLoanApplicationException.class, 
            () -> loanApplicationService.applyForLoan(request));
    }

    @Test
    void testProcessLoanApplication() {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        LoanApplicationResponse application = loanApplicationService.applyForLoan(request);
        LoanApplicationResponse processed = loanApplicationService.processLoanApplication(
            application.getApplicationId(), LoanApplication.ApprovalStatus.APPROVED);

        assertEquals(LoanApplication.ApprovalStatus.APPROVED, processed.getApprovalStatus());
    }

    @Test
    void testGetApplicationNotFound() {
        assertThrows(LoanApplicationNotFoundException.class, 
            () -> loanApplicationService.getApplicationStatus(999L));
    }

    @Test
    void testGetApplicationsByStatus() {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        loanApplicationService.applyForLoan(request);

        List<LoanApplicationResponse> pending = loanApplicationService
            .getApplicationsByStatus(LoanApplication.ApprovalStatus.PENDING);

        assertTrue(pending.size() >= 1);
    }
}