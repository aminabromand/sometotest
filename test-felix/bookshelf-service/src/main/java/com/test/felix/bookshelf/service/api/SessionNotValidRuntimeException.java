package com.test.felix.bookshelf.service.api;

public class SessionNotValidRuntimeException extends RuntimeException {
    public SessionNotValidRuntimeException(String errorMessage) {
        super(errorMessage);
    }
}
