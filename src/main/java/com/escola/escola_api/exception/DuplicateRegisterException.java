package com.escola.escola_api.exception;

public class DuplicateRegisterException extends RuntimeException {
    public DuplicateRegisterException(String message) {
        super(message);
    }
}
