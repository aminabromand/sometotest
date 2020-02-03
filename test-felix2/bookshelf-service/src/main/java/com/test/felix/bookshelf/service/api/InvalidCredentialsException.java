package com.test.felix.bookshelf.service.api;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String errorMessage) {
        super(errorMessage);
    }
}
