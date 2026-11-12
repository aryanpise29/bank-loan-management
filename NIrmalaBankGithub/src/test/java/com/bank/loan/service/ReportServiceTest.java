package com.bank.loan.service;

import com.bank.loan.dto.*;
import com.bank.loan.model.Customer;
import com.bank.loan.model.LoanApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ReportServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanProductService loanProductService;

    @Autowired
    private LoanApplicationService loanApplicationService;

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
    void testGenerateLoanReport() {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        loanApplicationService.applyForLoan(request);

        Map<String, Object> report = reportService.generateLoanReport();

        assertNotNull(report);
        assertTrue((Long) report.get("totalApplications") >= 1);
        assertNotNull(report.get("applicationsByStatus"));
        assertNotNull(report.get("totalApprovedAmount"));
    }

    @Test
    void testGenerateRepaymentReport() {
        Map<String, Object> report = reportService.generateRepaymentReport();

        assertNotNull(report);
        assertTrue((Long) report.get("totalRepayments") >= 0);
        assertNotNull(report.get("repaymentsByStatus"));
        assertNotNull(report.get("totalAmountCollected"));
        assertNotNull(report.get("overduePaymentsCount"));
    }

    @Test
    void testGenerateOutstandingLoansReport() {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        LoanApplicationResponse application = loanApplicationService.applyForLoan(request);
        loanApplicationService.processLoanApplication(
            application.getApplicationId(), LoanApplication.ApprovalStatus.APPROVED);

        Map<String, Object> report = reportService.generateOutstandingLoansReport();

        assertNotNull(report);
        assertTrue((Integer) report.get("totalOutstandingLoans") >= 0);
        assertNotNull(report.get("totalOutstandingAmount"));
        assertNotNull(report.get("outstandingByCustomer"));
    }

    @Test
    void testGenerateCustomerPortfolioReport() {
        LoanApplicationRequest request = new LoanApplicationRequest();
        request.setCustomerId(customerId);
        request.setLoanProductId(productId);
        request.setLoanAmount(new BigDecimal("100000"));
        request.setTenure(24);

        loanApplicationService.applyForLoan(request);

        Map<String, Object> report = reportService.generateCustomerPortfolioReport(customerId);

        assertNotNull(report);
        assertEquals(customerId, report.get("customerId"));
        assertEquals("John Doe", report.get("customerName"));
        assertTrue((Integer) report.get("totalApplications") >= 1);
        assertNotNull(report.get("applicationsByStatus"));
        assertNotNull(report.get("totalAppliedAmount"));
    }

    @Test
    void testGenerateCustomerPortfolioReportNotFound() {
        assertThrows(IllegalArgumentException.class, 
            () -> reportService.generateCustomerPortfolioReport(999L));
    }
}