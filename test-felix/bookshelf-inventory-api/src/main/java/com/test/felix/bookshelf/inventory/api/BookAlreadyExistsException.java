package com.test.felix.bookshelf.inventory.api;

public class BookAlreadyExistsException extends Exception { 
    public BookAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
