package com.bank.loan.service;

import com.bank.loan.dto.CustomerRequest;
import com.bank.loan.dto.CustomerResponse;
import com.bank.loan.exception.CustomerNotFoundException;
import com.bank.loan.model.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void testRegisterCustomer() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setAddress("123 Main St");
        request.setPassword("password123");

        CustomerResponse response = customerService.registerCustomer(request);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
        assertEquals(Customer.KycStatus.PENDING, response.getKycStatus());
    }

    @Test
    void testRegisterDuplicateEmail() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password123");

        customerService.registerCustomer(request);

        assertThrows(IllegalArgumentException.class, 
            () -> customerService.registerCustomer(request));
    }

    @Test
    void testGetCustomerDetails() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password123");

        CustomerResponse registered = customerService.registerCustomer(request);
        CustomerResponse response = customerService.getCustomerDetails(registered.getCustomerId());

        assertEquals("John Doe", response.getName());
        assertEquals("john@example.com", response.getEmail());
    }

    @Test
    void testGetCustomerNotFound() {
        assertThrows(CustomerNotFoundException.class, 
            () -> customerService.getCustomerDetails(999L));
    }

    @Test
    void testUpdateKycStatus() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password123");

        CustomerResponse registered = customerService.registerCustomer(request);
        CustomerResponse updated = customerService.updateKycStatus(
            registered.getCustomerId(), Customer.KycStatus.VERIFIED);

        assertEquals(Customer.KycStatus.VERIFIED, updated.getKycStatus());
    }

    @Test
    void testGetAllCustomers() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password123");

        customerService.registerCustomer(request);
        List<CustomerResponse> customers = customerService.getAllCustomers();

        assertTrue(customers.size() >= 1);
        assertTrue(customers.stream().anyMatch(c -> c.getEmail().equals("john@example.com")));
    }

    @Test
    void testUpdateCustomerPassword() {
        CustomerRequest request = new CustomerRequest();
        request.setName("John Doe");
        request.setEmail("john@example.com");
        request.setPhone("1234567890");
        request.setPassword("password123");

        CustomerResponse registered = customerService.registerCustomer(request);
        customerService.updateCustomerPassword(registered.getCustomerId(), "newPassword");

        Customer entity = customerService.getCustomerEntity(registered.getCustomerId());
        assertEquals("newPassword", entity.getPassword());
    }
}