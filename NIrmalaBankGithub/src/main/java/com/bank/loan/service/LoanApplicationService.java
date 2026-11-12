package com.bank.loan.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bank.loan.dto.LoanApplicationRequest;
import com.bank.loan.dto.LoanApplicationResponse;
import com.bank.loan.exception.InvalidLoanApplicationException;
import com.bank.loan.exception.LoanApplicationNotFoundException;
import com.bank.loan.model.Customer;
import com.bank.loan.model.LoanApplication;
import com.bank.loan.model.LoanProduct;
import com.bank.loan.repository.LoanApplicationRepository;

@Service
@Transactional
public class LoanApplicationService {
    @Autowired
    private LoanApplicationRepository loanApplicationRepository;
    
    @Autowired
    private CustomerService customerService;
    
    @Autowired
    private LoanProductService loanProductService;
    
    @Autowired
    private RepaymentService repaymentService;
    
    // Process new loan application from customer
    public LoanApplicationResponse applyForLoan(LoanApplicationRequest request) {
        // Check if customer exists and KYC is verified
        Customer customer = customerService.getCustomerEntity(request.getCustomerId());
        if (customer.getKycStatus() != Customer.KycStatus.VERIFIED) {
            throw new InvalidLoanApplicationException("Customer KYC must be verified before applying for loan");
        }
        
        // Check if loan product exists
        LoanProduct loanProduct = loanProductService.getLoanProductEntity(request.getLoanProductId());
        
        // Check if loan amount is within product limits
        if (request.getLoanAmount().compareTo(loanProduct.getMinAmount()) < 0 ||
            request.getLoanAmount().compareTo(loanProduct.getMaxAmount()) > 0) {
            throw new InvalidLoanApplicationException(
                "Loan amount must be between " + loanProduct.getMinAmount() + 
                " and " + loanProduct.getMaxAmount()
            );
        }
        
        // Check if customer has any pending applications
        List<LoanApplication> pendingApplications = loanApplicationRepository
                .findByCustomerAndStatus(request.getCustomerId(), LoanApplication.ApprovalStatus.PENDING);
        if (!pendingApplications.isEmpty()) {
            throw new InvalidLoanApplicationException("Customer has pending loan applications");
        }
        
        LoanApplication application = new LoanApplication();
        application.setCustomer(customer);
        application.setLoanProduct(loanProduct);
        application.setLoanAmount(request.getLoanAmount());
        application.setTenure(request.getTenure());
        application.setApplicationDate(LocalDate.now());
        application.setApprovalStatus(LoanApplication.ApprovalStatus.PENDING);
        
        LoanApplication savedApplication = loanApplicationRepository.save(application);
        return LoanApplicationResponse.fromEntity(savedApplication);
    }
    
    @Transactional(readOnly = true)
    // Get loan application status by ID
    public LoanApplicationResponse getApplicationStatus(Long applicationId) {
        LoanApplication application = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found with ID: " + applicationId));
        return LoanApplicationResponse.fromEntity(application);
    }
    
    // Approve or reject loan application by admin
    public LoanApplicationResponse processLoanApplication(Long applicationId, LoanApplication.ApprovalStatus status, String remarks) {
        LoanApplication application = loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found with ID: " + applicationId));
        
        if (application.getApprovalStatus() != LoanApplication.ApprovalStatus.PENDING) {
            throw new InvalidLoanApplicationException("Application has already been processed");
        }
        
        application.setApprovalStatus(status);
        application.setRemarks(remarks);
        LoanApplication updatedApplication = loanApplicationRepository.save(application);
        
        // If approved, generate repayment schedule
        if (status == LoanApplication.ApprovalStatus.APPROVED) {
            repaymentService.generateRepaymentSchedule(updatedApplication);
        }
        
        return LoanApplicationResponse.fromEntity(updatedApplication);
    }
    
    public LoanApplicationResponse processLoanApplication(Long applicationId, LoanApplication.ApprovalStatus status) {
        return processLoanApplication(applicationId, status, null);
    }
    
    // Get all loan applications for a customer
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getApplicationsByCustomer(Long customerId) {
        return loanApplicationRepository.findByCustomerCustomerId(customerId).stream()
                .map(LoanApplicationResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    // Get loan applications filtered by approval status
    @Transactional(readOnly = true)
    public List<LoanApplicationResponse> getApplicationsByStatus(LoanApplication.ApprovalStatus status) {
        return loanApplicationRepository.findByApprovalStatus(status).stream()
                .map(LoanApplicationResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    // Get loan application entity for internal use
    @Transactional(readOnly = true)
    public LoanApplication getLoanApplicationEntity(Long applicationId) {
        return loanApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new LoanApplicationNotFoundException("Loan application not found with ID: " + applicationId));
    }
}
