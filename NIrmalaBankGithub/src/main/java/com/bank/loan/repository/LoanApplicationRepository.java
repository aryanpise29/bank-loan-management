package com.bank.loan.repository;

import com.bank.loan.model.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    
    List<LoanApplication> findByCustomerCustomerId(Long customerId);
    
    List<LoanApplication> findByApprovalStatus(LoanApplication.ApprovalStatus status);
    
    List<LoanApplication> findByApplicationDateBetween(LocalDate startDate, LocalDate endDate);
    
    @Query("SELECT la FROM LoanApplication la WHERE la.customer.customerId = :customerId AND la.approvalStatus = :status")
    List<LoanApplication> findByCustomerAndStatus(Long customerId, LoanApplication.ApprovalStatus status);
    
    @Query("SELECT la FROM LoanApplication la WHERE la.loanProduct.loanProductId = :productId")
    List<LoanApplication> findByLoanProduct(Long productId);
}
