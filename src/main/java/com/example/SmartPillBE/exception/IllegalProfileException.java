package com.example.SmartPillBE.exception;

public class IllegalProfileException extends RuntimeException {
    public IllegalProfileException() {
    }

    public IllegalProfileException(String message) {
        super(message);
    }

    public IllegalProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalProfileException(Throwable cause) {
        super(cause);
    }

    public IllegalProfileException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
