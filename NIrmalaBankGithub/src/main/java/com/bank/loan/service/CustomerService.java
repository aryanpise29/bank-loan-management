package com.bank.loan.service;

import com.bank.loan.dto.CustomerRequest;
import com.bank.loan.dto.CustomerResponse;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    
    // Register new customer in system
    public CustomerResponse registerCustomer(CustomerRequest request) {
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Customer with email " + request.getEmail() + " already exists");
        }
        
        Customer customer = new Customer();
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        customer.setPassword(request.getPassword());
        customer.setRole("CUSTOMER");
        customer.setKycStatus(Customer.KycStatus.PENDING);
        
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerResponse.fromEntity(savedCustomer);
    }
    
    @Transactional(readOnly = true)
    // Get customer details by ID
    public CustomerResponse getCustomerDetails(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
        return CustomerResponse.fromEntity(customer);
    }
    
    // Update customer profile information
    public CustomerResponse updateCustomerProfile(Long customerId, CustomerRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
        
        // Check if email is being changed and if it already exists
        if (!customer.getEmail().equals(request.getEmail()) && 
            customerRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email " + request.getEmail() + " is already in use");
        }
        
        customer.setName(request.getName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setAddress(request.getAddress());
        
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerResponse.fromEntity(updatedCustomer);
    }
    
    // Update customer KYC verification status
    public CustomerResponse updateKycStatus(Long customerId, Customer.KycStatus kycStatus) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
        
        customer.setKycStatus(kycStatus);
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerResponse.fromEntity(updatedCustomer);
    }
    
    // Get list of all registered customers
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerResponse::fromEntity)
                .collect(Collectors.toList());
    }
    

    
    // Get customer entity for internal use
    @Transactional(readOnly = true)
    public Customer getCustomerEntity(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
    }
    
    // Update customer password
    public void updateCustomerPassword(Long customerId, String encodedPassword) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));
        customer.setPassword(encodedPassword);
        customer.setRole("CUSTOMER");
        customerRepository.save(customer);
    }

}
