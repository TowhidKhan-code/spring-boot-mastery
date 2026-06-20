package com.towhid.spring_mvc.day11.validation.exception;

// Thrown when request data is
// logically invalid
// Results in 400 Bad Request response
public class BadRequestException
        extends RuntimeException{

    public BadRequestException(String message) {
        super(message);
        // "Price cannot be negative"
        // "Stock cannot exceed 10000"
    }
}
