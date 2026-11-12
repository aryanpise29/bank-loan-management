package com.bank.loan.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "loan_product")
public class LoanProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanProductId")
    private Long loanProductId;
    
    @Column(name = "productName", nullable = false, length = 50)
    private String productName;
    
    @Column(name = "interestRate", nullable = false, precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @Column(name = "minAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal minAmount;
    
    @Column(name = "maxAmount", nullable = false, precision = 10, scale = 2)
    private BigDecimal maxAmount;
    
    @Column(name = "tenure", nullable = true)
    private Integer tenure;
    
    @OneToMany(mappedBy = "loanProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplications;

    public LoanProduct() {}

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

    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(List<LoanApplication> loanApplications) {
        this.loanApplications = loanApplications;
    }
    
    public Integer getTenure() {
        return tenure;
    }
    
    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }
}
