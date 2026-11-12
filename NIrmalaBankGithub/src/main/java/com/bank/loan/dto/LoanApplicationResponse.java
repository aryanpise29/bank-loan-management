package com.bank.loan.dto;

import com.bank.loan.model.LoanApplication;
import java.math.BigDecimal;
import java.time.LocalDate;

public class LoanApplicationResponse {
    
    private Long applicationId;
    private Long customerId;
    private String customerName;
    private Long loanProductId;
    private String productName;
    private BigDecimal loanAmount;
    private Integer tenure;
    private LocalDate applicationDate;
    private LoanApplication.ApprovalStatus approvalStatus;
    
    public static LoanApplicationResponse fromEntity(LoanApplication application) {
        LoanApplicationResponse response = new LoanApplicationResponse();
        response.setApplicationId(application.getApplicationId());
        response.setCustomerId(application.getCustomer().getCustomerId());
        response.setCustomerName(application.getCustomer().getName());
        response.setLoanProductId(application.getLoanProduct().getLoanProductId());
        response.setProductName(application.getLoanProduct().getProductName());
        response.setLoanAmount(application.getLoanAmount());
        response.setTenure(application.getTenure());
        response.setApplicationDate(application.getApplicationDate());
        response.setApprovalStatus(application.getApprovalStatus());
        return response;
    }

    public LoanApplicationResponse() {}

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(Long loanProductId) {
        this.loanProductId = loanProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LoanApplication.ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(LoanApplication.ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
    
    public Integer getTenure() {
        return tenure;
    }
    
    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }
}
