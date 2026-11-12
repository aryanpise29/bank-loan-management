package com.bank.loan.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentRequest {
    
    @NotNull(message = "Repayment ID is required")
    private Long repaymentId;
    
    @NotNull(message = "Payment amount is required")
    @Positive(message = "Payment amount must be positive")
    private BigDecimal paymentAmount;
    
    private LocalDate paymentDate;
    
    public PaymentRequest() {
        this.paymentDate = LocalDate.now();
    }
    
    public Long getRepaymentId() {
        return repaymentId;
    }
    
    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }
    
    public BigDecimal getPaymentAmount() {
        return paymentAmount;
    }
    
    public void setPaymentAmount(BigDecimal paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = LocalDate.parse(paymentDate);
    }
}