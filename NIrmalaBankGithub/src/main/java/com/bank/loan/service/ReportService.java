package com.bank.loan.service;

import com.bank.loan.model.Customer;
import com.bank.loan.model.LoanApplication;
import com.bank.loan.model.Repayment;
import com.bank.loan.repository.CustomerRepository;
import com.bank.loan.repository.LoanApplicationRepository;
import com.bank.loan.repository.RepaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class ReportService {
	@Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    
    @Autowired
    private RepaymentRepository repaymentRepository;
    
    public Map<String, Object> generateLoanReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Total applications
        long totalApplications = loanApplicationRepository.count();
        report.put("totalApplications", totalApplications);
        
        // Applications by status
        Map<String, Long> applicationsByStatus = new HashMap<>();
        applicationsByStatus.put("PENDING", (long) loanApplicationRepository.findByApprovalStatus(LoanApplication.ApprovalStatus.PENDING).size());
        applicationsByStatus.put("APPROVED", (long) loanApplicationRepository.findByApprovalStatus(LoanApplication.ApprovalStatus.APPROVED).size());
        applicationsByStatus.put("REJECTED", (long) loanApplicationRepository.findByApprovalStatus(LoanApplication.ApprovalStatus.REJECTED).size());
        report.put("applicationsByStatus", applicationsByStatus);
        
        // Total loan amount approved
        BigDecimal totalApprovedAmount = loanApplicationRepository.findByApprovalStatus(LoanApplication.ApprovalStatus.APPROVED)
                .stream()
                .map(LoanApplication::getLoanAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        report.put("totalApprovedAmount", totalApprovedAmount);
        
        // Average loan amount
        if (totalApplications > 0) {
            BigDecimal totalAmount = loanApplicationRepository.findAll()
                    .stream()
                    .map(LoanApplication::getLoanAmount)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal averageAmount = totalAmount.divide(BigDecimal.valueOf(totalApplications), 2, RoundingMode.HALF_UP);
            report.put("averageLoanAmount", averageAmount);
        }
        
        return report;
    }
    
    public Map<String, Object> generateRepaymentReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Total repayments
        long totalRepayments = repaymentRepository.count();
        report.put("totalRepayments", totalRepayments);
        
        // Repayments by status
        Map<String, Long> repaymentsByStatus = new HashMap<>();
        repaymentsByStatus.put("PENDING", (long) repaymentRepository.findByPaymentStatus(Repayment.PaymentStatus.PENDING).size());
        repaymentsByStatus.put("COMPLETED", (long) repaymentRepository.findByPaymentStatus(Repayment.PaymentStatus.COMPLETED).size());
        report.put("repaymentsByStatus", repaymentsByStatus);
        
        // Total amount collected
        BigDecimal totalCollected = repaymentRepository.findByPaymentStatus(Repayment.PaymentStatus.COMPLETED)
                .stream()
                .map(Repayment::getAmountDue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        report.put("totalAmountCollected", totalCollected);
        
        // Overdue payments (past due date and still pending)
        List<Repayment> overduePayments = repaymentRepository.findByDueDateBeforeAndPaymentStatus(
                LocalDate.now(), Repayment.PaymentStatus.PENDING);
        report.put("overduePaymentsCount", overduePayments.size());
        
        BigDecimal overdueAmount = overduePayments.stream()
                .map(Repayment::getAmountDue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        report.put("overdueAmount", overdueAmount);
        
        return report;
    }
    
    public Map<String, Object> generateOutstandingLoansReport() {
        Map<String, Object> report = new HashMap<>();
        
        // Get all approved applications
        List<LoanApplication> approvedApplications = loanApplicationRepository.findByApprovalStatus(LoanApplication.ApprovalStatus.APPROVED);
        
        int totalOutstandingLoans = 0;
        BigDecimal totalOutstandingAmount = BigDecimal.ZERO;
        
        for (LoanApplication application : approvedApplications) {
            Double outstandingBalanceDouble = repaymentRepository.calculateOutstandingBalance(application.getApplicationId());
            if (outstandingBalanceDouble != null && outstandingBalanceDouble > 0) {
                BigDecimal outstandingBalance = BigDecimal.valueOf(outstandingBalanceDouble);
                totalOutstandingLoans++;
                totalOutstandingAmount = totalOutstandingAmount.add(outstandingBalance);
            }
        }
        
        report.put("totalOutstandingLoans", totalOutstandingLoans);
        report.put("totalOutstandingAmount", totalOutstandingAmount);
        
        // Outstanding loans by customer
        Map<String, BigDecimal> outstandingByCustomer = new HashMap<>();
        for (LoanApplication application : approvedApplications) {
            Double outstandingBalanceDouble = repaymentRepository.calculateOutstandingBalance(application.getApplicationId());
            if (outstandingBalanceDouble != null && outstandingBalanceDouble > 0) {
                BigDecimal outstandingBalance = BigDecimal.valueOf(outstandingBalanceDouble);
                String customerName = application.getCustomer().getName();
                outstandingByCustomer.merge(customerName, outstandingBalance, BigDecimal::add);
            }
        }
        report.put("outstandingByCustomer", outstandingByCustomer);
        
        return report;
    }
    
    public Map<String, Object> generateCustomerPortfolioReport(Long customerId) {
        Map<String, Object> report = new HashMap<>();
        
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found with ID: " + customerId));
        
        report.put("customerId", customerId);
        report.put("customerName", customer.getName());
        report.put("kycStatus", customer.getKycStatus());
        
        // Customer's loan applications
        List<LoanApplication> customerApplications = loanApplicationRepository.findByCustomerCustomerId(customerId);
        report.put("totalApplications", customerApplications.size());
        
        // Applications by status
        Map<String, Long> applicationsByStatus = new HashMap<>();
        applicationsByStatus.put("PENDING", customerApplications.stream()
                .filter(app -> app.getApprovalStatus() == LoanApplication.ApprovalStatus.PENDING)
                .count());
        applicationsByStatus.put("APPROVED", customerApplications.stream()
                .filter(app -> app.getApprovalStatus() == LoanApplication.ApprovalStatus.APPROVED)
                .count());
        applicationsByStatus.put("REJECTED", customerApplications.stream()
                .filter(app -> app.getApprovalStatus() == LoanApplication.ApprovalStatus.REJECTED)
                .count());
        report.put("applicationsByStatus", applicationsByStatus);
        
        // Total loan amount applied for
        BigDecimal totalAppliedAmount = customerApplications.stream()
                .map(LoanApplication::getLoanAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        report.put("totalAppliedAmount", totalAppliedAmount);
        
        // Outstanding balance
        BigDecimal totalOutstanding = BigDecimal.ZERO;
        for (LoanApplication application : customerApplications) {
            if (application.getApprovalStatus() == LoanApplication.ApprovalStatus.APPROVED) {
                Double outstandingBalanceDouble = repaymentRepository.calculateOutstandingBalance(application.getApplicationId());
                if (outstandingBalanceDouble != null) {
                    BigDecimal outstandingBalance = BigDecimal.valueOf(outstandingBalanceDouble);
                    totalOutstanding = totalOutstanding.add(outstandingBalance);
                }
            }
        }
        report.put("totalOutstandingBalance", totalOutstanding);
        
        return report;
    }
}
