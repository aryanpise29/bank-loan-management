package com.bank.loan.service;

import com.bank.loan.dto.*;
import com.bank.loan.exception.RepaymentNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.model.LoanApplication;
import com.bank.loan.model.Repayment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class RepaymentServiceTest {

    @Autowired
    private RepaymentService repaymentService;

    @Autowired
    private LoanApplicationService loanApplicationService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanProductService loanProductService;

    private LoanApplication approvedApplication;

    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setName("John Doe");
        customerRequest.setEmail("john@example.com");
        customerRequest.setPhone("1234567890");
        customerRequest.setPassword("password123");

        CustomerResponse customer = customerService.registerCustomer(customerRequest);
        customerService.updateKycStatus(customer.getCustomerId(), Customer.KycStatus.VERIFIED);

        LoanProductRequest productRequest = new LoanProductRequest();
        productRequest.setProductName("Personal Loan");
        productRequest.setInterestRate(new BigDecimal("10.5"));
        productRequest.setMinAmount(new BigDecimal("10000"));
        productRequest.setMaxAmount(new BigDecimal("500000"));

        LoanProductResponse product = loanProductService.addLoanProduct(productRequest);

        LoanApplicationRequest appRequest = new LoanApplicationRequest();
        appRequest.setCustomerId(customer.getCustomerId());
        appRequest.setLoanProductId(product.getLoanProductId());
        appRequest.setLoanAmount(new BigDecimal("100000"));
        appRequest.setTenure(12);

        LoanApplicationResponse application = loanApplicationService.applyForLoan(appRequest);
        loanApplicationService.processLoanApplication(
            application.getApplicationId(), LoanApplication.ApprovalStatus.APPROVED);

        approvedApplication = loanApplicationService.getLoanApplicationEntity(application.getApplicationId());
    }

    @Test
    void testGenerateRepaymentSchedule() {
        repaymentService.generateRepaymentSchedule(approvedApplication);

        List<RepaymentResponse> schedule = repaymentService.getRepaymentSchedule(approvedApplication.getApplicationId());

        assertEquals(12, schedule.size());
        assertTrue(schedule.stream().allMatch(r -> r.getPaymentStatus() == Repayment.PaymentStatus.PENDING));
    }

    @Test
    void testMakePayment() {
        repaymentService.generateRepaymentSchedule(approvedApplication);
        List<RepaymentResponse> schedule = repaymentService.getRepaymentSchedule(approvedApplication.getApplicationId());

        RepaymentResponse firstPayment = schedule.get(0);

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setRepaymentId(firstPayment.getRepaymentId());
        paymentRequest.setPaymentAmount(firstPayment.getAmountDue());
        paymentRequest.setPaymentDate(LocalDate.now());

        RepaymentResponse paid = repaymentService.makePayment(paymentRequest);

        assertEquals(Repayment.PaymentStatus.COMPLETED, paid.getPaymentStatus());
    }

    @Test
    void testMakePaymentNotFound() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setRepaymentId(999L);
        paymentRequest.setPaymentAmount(new BigDecimal("1000"));
        paymentRequest.setPaymentDate(LocalDate.now());

        assertThrows(RepaymentNotFoundException.class, 
            () -> repaymentService.makePayment(paymentRequest));
    }

    @Test
    void testGetOutstandingBalance() {
        repaymentService.generateRepaymentSchedule(approvedApplication);

        BigDecimal outstanding = repaymentService.getOutstandingBalance(approvedApplication.getApplicationId());

        assertTrue(outstanding.compareTo(BigDecimal.ZERO) > 0);
    }
}