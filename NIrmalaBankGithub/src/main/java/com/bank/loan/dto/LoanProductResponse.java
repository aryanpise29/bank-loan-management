package com.bank.loan.dto;

import com.bank.loan.model.LoanProduct;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanProductResponse {
    
    private Long loanProductId;
    private String productName;
    private BigDecimal interestRate;
    private BigDecimal minAmount;
    private BigDecimal maxAmount;
    private LocalDateTime createdAt;
    
    public static LoanProductResponse fromEntity(LoanProduct product) {
        LoanProductResponse response = new LoanProductResponse();
        response.setLoanProductId(product.getLoanProductId());
        response.setProductName(product.getProductName());
        response.setInterestRate(product.getInterestRate());
        response.setMinAmount(product.getMinAmount());
        response.setMaxAmount(product.getMaxAmount());
        return response;
    }

    public LoanProductResponse() {}

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
