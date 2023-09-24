package com.heuron.backend.exception;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        super(message, null, false, false);
    }
}

