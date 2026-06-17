package com.towhid.spring_data.day08.relationships.exception;

// Custom exception for insufficient balance
// RuntimeException so Spring auto-rolls back!
public class InsufficientBalanceException
        extends RuntimeException {

    private final Double available;
    private final Double requested;

    public InsufficientBalanceException(
            Double available, Double requested) {
        super(String.format(
                "Insufficient balance. Available: $%.2f, Requested: $%.2f",
                available, requested));
        this.available = available;
        this.requested = requested;
    }

    public Double getAvailable() {
        return available;
    }

    public Double getRequested() {
        return requested;
    }
}