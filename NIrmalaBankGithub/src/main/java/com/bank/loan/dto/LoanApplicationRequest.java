package com.bank.loan.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class LoanApplicationRequest {
    
    @NotNull(message = "Customer ID is required")
    private Long customerId;
    
    @NotNull(message = "Loan Product ID is required")
    private Long loanProductId;
    
    @NotNull(message = "Loan amount is required")
    @DecimalMin(value = "0.0", message = "Loan amount must be non-negative")
    private BigDecimal loanAmount;
    
    @NotNull(message = "Tenure is required")
    private Integer tenure;

    public LoanApplicationRequest() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(Long loanProductId) {
        this.loanProductId = loanProductId;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }
    
    public Integer getTenure() {
        return tenure;
    }
    
    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }
}
