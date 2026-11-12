package com.bank.loan.exception;

public class LoanProductNotFoundException extends RuntimeException {
    public LoanProductNotFoundException(String message) {
        super(message);
    }
    
    public LoanProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
