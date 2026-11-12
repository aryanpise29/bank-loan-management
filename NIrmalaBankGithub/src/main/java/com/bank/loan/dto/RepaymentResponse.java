package com.bank.loan.dto;

import com.bank.loan.model.Repayment;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RepaymentResponse {
    
    private Long repaymentId;
    private Long applicationId;
    private LocalDate dueDate;
    private BigDecimal amountDue;
    private LocalDate paymentDate;
    private Repayment.PaymentStatus paymentStatus;
    
    public static RepaymentResponse fromEntity(Repayment repayment) {
        RepaymentResponse response = new RepaymentResponse();
        response.setRepaymentId(repayment.getRepaymentId());
        response.setApplicationId(repayment.getLoanApplication().getApplicationId());
        response.setDueDate(repayment.getDueDate());
        response.setAmountDue(repayment.getAmountDue());
        response.setPaymentDate(repayment.getPaymentDate());
        response.setPaymentStatus(repayment.getPaymentStatus());
        return response;
    }

    public RepaymentResponse() {}

    public Long getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(BigDecimal amountDue) {
        this.amountDue = amountDue;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Repayment.PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Repayment.PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
