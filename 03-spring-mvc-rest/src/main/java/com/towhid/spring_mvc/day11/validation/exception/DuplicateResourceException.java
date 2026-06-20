package com.towhid.spring_mvc.day11.validation.exception;

// Thrown when trying to create
// something that already exists
// Results in 409 Conflict response
public class DuplicateResourceException
        extends RuntimeException{

    public DuplicateResourceException(String message) {
        super(message);
        // "Product already exists with name: 'iPhone'"
    }
}
