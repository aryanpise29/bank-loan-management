package com.bank.loan.service;


import com.bank.loan.dto.PaymentRequest;
import com.bank.loan.dto.RepaymentResponse;
import com.bank.loan.exception.RepaymentNotFoundException;
import com.bank.loan.exception.LoanApplicationNotFoundException;

import com.bank.loan.model.LoanApplication;
import com.bank.loan.model.Repayment;
import com.bank.loan.repository.RepaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RepaymentService {
    
	@Autowired
    private RepaymentRepository repaymentRepository;
    
    // Generate EMI schedule for approved loan
    public void generateRepaymentSchedule(LoanApplication application) {
        if (application.getApprovalStatus() != LoanApplication.ApprovalStatus.APPROVED) {
            throw new IllegalArgumentException("Cannot generate repayment schedule for non-approved application");
        }
        
        // Calculate EMI amount using compound interest formula
        BigDecimal principal = application.getLoanAmount();
        BigDecimal monthlyInterestRate = application.getLoanProduct().getInterestRate()
                .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(12), 6, RoundingMode.HALF_UP);
        int tenureInMonths = application.getTenure();
        
        BigDecimal monthlyPayment = calculateEMI(principal, monthlyInterestRate, tenureInMonths);
        
        // Create monthly EMI schedule for loan tenure
        List<Repayment> repayments = new ArrayList<>();
        LocalDate startDate = LocalDate.now().plusMonths(1); // First payment due next month
        
        for (int i = 0; i < tenureInMonths; i++) {
            Repayment repayment = new Repayment();
            repayment.setLoanApplication(application);
            repayment.setDueDate(startDate.plusMonths(i));
            repayment.setAmountDue(monthlyPayment);
            repayment.setPaymentStatus(Repayment.PaymentStatus.PENDING);
            repayments.add(repayment);
        }
        
        repaymentRepository.saveAll(repayments);
    }
    
    // Calculate monthly EMI using compound interest formula
    private BigDecimal calculateEMI(BigDecimal principal, BigDecimal monthlyInterestRate, int tenureInMonths) {
        if (monthlyInterestRate.compareTo(BigDecimal.ZERO) == 0) {
            return principal.divide(BigDecimal.valueOf(tenureInMonths), 2, RoundingMode.HALF_UP);
        }
        
        BigDecimal emi = principal.multiply(monthlyInterestRate)
                .multiply(BigDecimal.ONE.add(monthlyInterestRate).pow(tenureInMonths))
                .divide(BigDecimal.ONE.add(monthlyInterestRate).pow(tenureInMonths).subtract(BigDecimal.ONE), 2, RoundingMode.HALF_UP);
        
        return emi;
    }
    
    // Get complete repayment schedule for a loan
    @Transactional(readOnly = true)
    public List<RepaymentResponse> getRepaymentSchedule(Long applicationId) {
        List<Repayment> repayments = repaymentRepository.findByLoanApplicationApplicationId(applicationId);
        if (repayments.isEmpty()) {
            throw new LoanApplicationNotFoundException("No repayment schedule found for application ID: " + applicationId);
        }
        
        return repayments.stream()
                .map(RepaymentResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    // Process customer payment for EMI
    public RepaymentResponse makePayment(PaymentRequest request) {
        System.out.println("Processing payment for repayment ID: " + request.getRepaymentId());
        
        Repayment repayment = repaymentRepository.findById(request.getRepaymentId())
                .orElseThrow(() -> new RepaymentNotFoundException("Repayment not found with ID: " + request.getRepaymentId()));
        
        System.out.println("Current payment status: " + repayment.getPaymentStatus());
        
        if (repayment.getPaymentStatus() == Repayment.PaymentStatus.COMPLETED) {
            throw new IllegalArgumentException("Payment has already been made for this repayment");
        }
        
        if (request.getPaymentAmount().compareTo(repayment.getAmountDue()) != 0) {
            throw new IllegalArgumentException("Payment amount must equal the due amount: " + repayment.getAmountDue());
        }
        
        repayment.setPaymentDate(request.getPaymentDate());
        repayment.setPaymentStatus(Repayment.PaymentStatus.COMPLETED);
        
        System.out.println("Setting payment status to COMPLETED");
        
        Repayment savedRepayment = repaymentRepository.save(repayment);
        
        System.out.println("Saved payment status: " + savedRepayment.getPaymentStatus());
        
        return RepaymentResponse.fromEntity(savedRepayment);
    }
    
    // Calculate remaining loan balance
    @Transactional(readOnly = true)
    public BigDecimal getOutstandingBalance(Long applicationId) {
        Double outstandingAmount = repaymentRepository.calculateOutstandingBalance(applicationId);
        return outstandingAmount != null ? BigDecimal.valueOf(outstandingAmount) : BigDecimal.ZERO;
    }
}
