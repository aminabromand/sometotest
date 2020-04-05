package com.test.felix.bookshelf.service.api;

public class BookInventoryNotRegisteredRuntimeException extends RuntimeException {
    public BookInventoryNotRegisteredRuntimeException(String errorMessage) {
        super(errorMessage);
    }
}
