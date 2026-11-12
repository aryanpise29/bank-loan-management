package com.bank.loan.exception;

public class InvalidLoanApplicationException extends RuntimeException {
    public InvalidLoanApplicationException(String message) {
        super(message);
    }
    
    public InvalidLoanApplicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
