package com.example.SmartPillBE.exception;

public class IllegalPillException extends RuntimeException {
    public IllegalPillException() {
    }

    public IllegalPillException(String message) {
        super(message);
    }

    public IllegalPillException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalPillException(Throwable cause) {
        super(cause);
    }

    public IllegalPillException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
