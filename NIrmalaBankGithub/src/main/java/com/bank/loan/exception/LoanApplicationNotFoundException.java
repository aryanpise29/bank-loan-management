package com.bank.loan.exception;

public class LoanApplicationNotFoundException extends RuntimeException {
    public LoanApplicationNotFoundException(String message) {
        super(message);
    }
    
    public LoanApplicationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
