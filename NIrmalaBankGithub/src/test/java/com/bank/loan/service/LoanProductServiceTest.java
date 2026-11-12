package com.bank.loan.service;

import com.bank.loan.dto.LoanProductRequest;
import com.bank.loan.dto.LoanProductResponse;
import com.bank.loan.exception.LoanProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class LoanProductServiceTest {

    @Autowired
    private LoanProductService loanProductService;

    @Test
    void testAddLoanProduct() {
        LoanProductRequest request = new LoanProductRequest();
        request.setProductName("Personal Loan");
        request.setInterestRate(new BigDecimal("10.5"));
        request.setMinAmount(new BigDecimal("10000"));
        request.setMaxAmount(new BigDecimal("500000"));

        LoanProductResponse response = loanProductService.addLoanProduct(request);

        assertNotNull(response);
        assertEquals("Personal Loan", response.getProductName());
        assertEquals(new BigDecimal("10.5"), response.getInterestRate());
        
    }

    @Test
    void testAddLoanProductInvalidAmounts() {
        LoanProductRequest request = new LoanProductRequest();
        request.setProductName("Invalid Loan");
        request.setInterestRate(new BigDecimal("10.5"));
        request.setMinAmount(new BigDecimal("500000"));
        request.setMaxAmount(new BigDecimal("10000"));

        assertThrows(IllegalArgumentException.class, 
            () -> loanProductService.addLoanProduct(request));
    }

    @Test
    void testGetAllLoanProducts() {
        LoanProductRequest request = new LoanProductRequest();
        request.setProductName("Personal Loan");
        request.setInterestRate(new BigDecimal("10.5"));
        request.setMinAmount(new BigDecimal("10000"));
        request.setMaxAmount(new BigDecimal("500000"));

        loanProductService.addLoanProduct(request);
        List<LoanProductResponse> products = loanProductService.getAllLoanProducts();

        assertTrue(products.size() >= 1);
        assertTrue(products.stream().anyMatch(p -> p.getProductName().equals("Personal Loan")));
    }

    @Test
    void testGetLoanProductEntityNotFound() {
        assertThrows(LoanProductNotFoundException.class, 
            () -> loanProductService.getLoanProductEntity(999L));
    }
}