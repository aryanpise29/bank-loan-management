package com.bank.loan.repository;

import com.bank.loan.model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    
    List<Repayment> findByLoanApplicationApplicationId(Long applicationId);
    
    List<Repayment> findByPaymentStatus(Repayment.PaymentStatus status);
    
    List<Repayment> findByDueDateBeforeAndPaymentStatus(LocalDate date, Repayment.PaymentStatus status);
    

    
    @Query("SELECT SUM(r.amountDue) FROM Repayment r WHERE r.loanApplication.applicationId = :applicationId AND r.paymentStatus = 'PENDING'")
    Double calculateOutstandingBalance(Long applicationId);
}
