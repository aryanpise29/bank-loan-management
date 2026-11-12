package com.bank.loan.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "repayment")
public class Repayment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repaymentId")
    private Long repaymentId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applicationId", nullable = false)
    private LoanApplication loanApplication;
    
    @Column(name = "dueDate", nullable = false)
    private LocalDate dueDate;
    
    @Column(name = "amountDue", nullable = false, precision = 10, scale = 2)
    private BigDecimal amountDue;
    
    @Column(name = "paymentDate")
    private LocalDate paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "paymentStatus", nullable = false)
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    
    public enum PaymentStatus {
        PENDING, COMPLETED
    }

    public Repayment() {}

    public Long getRepaymentId() {
        return repaymentId;
    }

    public void setRepaymentId(Long repaymentId) {
        this.repaymentId = repaymentId;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
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

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
