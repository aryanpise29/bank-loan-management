package com.bank.loan.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "customer")
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long customerId;
    
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;
    
    @Column(name = "phone", nullable = false, length = 15)
    private String phone;
    
    @Column(name = "address", columnDefinition = "TEXT")
    private String address;
    
    @Column(name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "kycStatus", nullable = false)
    private KycStatus kycStatus = KycStatus.PENDING;
    
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LoanApplication> loanApplications;
    
    @Column(name = "role")
    private String role = "CUSTOMER";
    
    public enum KycStatus {
        PENDING, VERIFIED
    }

    public Customer() {}

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }

    public List<LoanApplication> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(List<LoanApplication> loanApplications) {
        this.loanApplications = loanApplications;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
