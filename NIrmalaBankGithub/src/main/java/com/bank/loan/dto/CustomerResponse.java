package com.bank.loan.dto;

import com.bank.loan.model.Customer;
import java.time.LocalDateTime;


public class CustomerResponse {
    
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Customer.KycStatus kycStatus;
    private LocalDateTime createdAt;
    
    public static CustomerResponse fromEntity(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setCustomerId(customer.getCustomerId());
        response.setName(customer.getName());
        response.setEmail(customer.getEmail());
        response.setPhone(customer.getPhone());
        response.setAddress(customer.getAddress());
        response.setKycStatus(customer.getKycStatus());
        return response;
    }
public CustomerResponse() {}

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

    public Customer.KycStatus getKycStatus() {
        return kycStatus;
    }

    public void setKycStatus(Customer.KycStatus kycStatus) {
        this.kycStatus = kycStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
