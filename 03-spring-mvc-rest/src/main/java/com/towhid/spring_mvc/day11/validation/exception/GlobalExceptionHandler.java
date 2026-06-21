package com.towhid.spring_mvc.day11.validation.exception;

import com.towhid.spring_mvc.day11.validation.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────
    // 1. REQUEST BODY VALIDATION ERRORS
    // @Valid on DTOs
    // ─────────────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach((FieldError error) -> {
                    String field = error.getField();
                    String message = error.getDefaultMessage();
                    errors.put(field, message);
                });

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation Failed",
                "Input validation failed. Check errors.",
                errors,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ─────────────────────────────────────────
    // Practice Task 3
    // 2. URL PARAMETER VALIDATION ERRORS
    // @PathVariable / @RequestParam validation
    // ─────────────────────────────────────────
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse>
    handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            // Example property path:
            // getProductById.id
            // searchProducts.keyword
            String fullPath = violation.getPropertyPath().toString();

            // Extract only the parameter name after the last dot
            String field = fullPath.contains(".")
                    ? fullPath.substring(fullPath.lastIndexOf('.') + 1)
                    : fullPath;

            errors.put(field, violation.getMessage());
        }

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation Failed",
                "URL parameter validation failed. Check errors.",
                errors,
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ─────────────────────────────────────────
    // 3. RESOURCE NOT FOUND
    // ─────────────────────────────────────────
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleResourceNotFound(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                404,
                "Not Found",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // ─────────────────────────────────────────
    // 4. DUPLICATE RESOURCE
    // ─────────────────────────────────────────
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse>
    handleDuplicateResource(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                409,
                "Conflict",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(response);
    }

    // ─────────────────────────────────────────
    // 5. BAD REQUEST
    // ─────────────────────────────────────────
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse>
    handleBadRequest(
            BadRequestException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ─────────────────────────────────────────
    // Practice Task 2
    // 6. INSUFFICIENT STOCK
    // ─────────────────────────────────────────
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorResponse>
    handleInsufficientStock(
            InsufficientStockException ex,
            HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                400,
                "Insufficient Stock",
                ex.getMessage(),
                request.getRequestURI());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
    }

    // ─────────────────────────────────────────
    // 7. ILLEGAL ARGUMENT
    // ─────────────────────────────────────────
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse>
    handleIllegalArgument(
            IllegalArgumentException ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                400,
                "Bad Request",
                ex.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ─────────────────────────────────────────
    // 8. CATCH ALL
    // ─────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        System.err.println("Unexpected error: "
                + ex.getMessage());

        ErrorResponse response = new ErrorResponse(
                500,
                "Internal Server Error",
                "Something went wrong. Please try again.",
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}