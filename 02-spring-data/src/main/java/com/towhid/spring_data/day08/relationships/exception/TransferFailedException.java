package com.towhid.spring_data.day08.relationships.exception;

// Simulates a failure during transfer
// Used to demonstrate rollback
public class TransferFailedException
        extends RuntimeException {

    public TransferFailedException(String message) {
        super(message);
    }
}