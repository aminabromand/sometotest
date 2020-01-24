package com.test.felix.bookshelf.inventory.api;

public class InvalidBookException extends Exception { 
    public InvalidBookException(String errorMessage) {
        super(errorMessage);
    }
}
