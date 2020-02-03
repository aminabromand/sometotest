package com.test.felix.bookshelf.inventory.api;

public class BookNotFoundException extends Exception { 
    public BookNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
