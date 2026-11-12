package com.bank.loan.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class LoanProductRequest {
    
    @NotBlank(message = "Product name is required")
    @Size(max = 50, message = "Product name must not exceed 50 characters")
    private String productName;
    
    @NotNull(message = "Interest rate is required")
    @DecimalMin(value = "0.0", message = "Interest rate must be non-negative")
    private BigDecimal interestRate;
    
    @NotNull(message = "Minimum amount is required")
    @DecimalMin(value = "0.0", message = "Minimum amount must be non-negative")
    private BigDecimal minAmount;
    
    @NotNull(message = "Maximum amount is required")
    @DecimalMin(value = "0.0", message = "Maximum amount must be non-negative")
    private BigDecimal maxAmount;

    public LoanProductRequest() {}

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }
}
