package com.towhid.spring_mvc.day11.validation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

// Structured error response
// Client always gets useful info
// when something goes wrong
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {

    private boolean success = false;
    // always false for errors

    private int status;
    // HTTP status code
    // 400, 404, 409, 500 etc

    private String error;
    // short error type name
    // "Not Found", "Bad Request" etc

    private String message;
    // human-readable error message
    // "Product not found with id: 5"

    private Map<String, String> validationErrors;
    // field-specific validation errors
    // { "name": "Name cannot be blank",
    //   "price": "Price must be positive" }
    // null if not a validation error

    private LocalDateTime timestamp;
    // when error occurred

    private String path;
    // which URL caused the error
    // "/api/products/999"

    // Constructor for general errors
    public ErrorResponse(int status, String error,
                         String message, String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }


    // Constructor for validation errors
    public ErrorResponse(int status, String error,
                         String message,
                         Map<String, String> validationErrors,
                         String path) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.validationErrors = validationErrors;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
