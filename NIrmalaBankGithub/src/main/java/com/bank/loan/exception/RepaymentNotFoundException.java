package com.bank.loan.exception;

public class RepaymentNotFoundException extends RuntimeException {
    public RepaymentNotFoundException(String message) {
        super(message);
    }
    
    public RepaymentNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
