package com.bank.loan.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "loan_application")
public class LoanApplication {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "applicationId")
    private Long applicationId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerId", nullable = false)
    private Customer customer;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loanProductId", nullable = false)
    private LoanProduct loanProduct;
    
    @Column(name = "loanAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal loanAmount;
    
    @Column(name = "applicationDate", nullable = false)
    private LocalDate applicationDate;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "approvalStatus", nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    
    @OneToMany(mappedBy = "loanApplication", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Repayment> repayments;
    
    @Column(name = "remarks")
    private String remarks;
    
    @Column(name = "tenure", nullable = false)
    private Integer tenure;
    
    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

    public LoanApplication() {}

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LoanProduct getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(LoanProduct loanProduct) {
        this.loanProduct = loanProduct;
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

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public List<Repayment> getRepayments() {
        return repayments;
    }

    public void setRepayments(List<Repayment> repayments) {
        this.repayments = repayments;
    }
    
    public String getRemarks() {
        return remarks;
    }
    
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    public Integer getTenure() {
        return tenure;
    }
    
    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }
}
