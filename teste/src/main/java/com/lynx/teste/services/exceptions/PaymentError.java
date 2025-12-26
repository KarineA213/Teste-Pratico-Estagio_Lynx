package com.lynx.teste.services.exceptions;

public class PaymentError extends RuntimeException {
    public PaymentError(String message) {
        super(message);
    }
}
