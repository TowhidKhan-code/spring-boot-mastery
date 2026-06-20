package com.towhid.spring_mvc.day11.validation.exception;

import com.towhid.spring_mvc.day11.validation.dto.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice =
// @ControllerAdvice + @ResponseBody
// Watches ALL controllers
// Catches exceptions from ANY controller
// Handles them in ONE place
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ─────────────────────────────────────────
    // 1. VALIDATION ERRORS
    // Thrown when @Valid fails
    // Returns 400 Bad Request
    // ─────────────────────────────────────────
    @ExceptionHandler(MethodArgumentNotValidException.class)
    // @ExceptionHandler = "when THIS exception
    // is thrown anywhere, run THIS method"
    public ResponseEntity<ErrorResponse>
    handleValidationErrors(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {
        // HttpServletRequest = info about
        // the HTTP request that caused error

        // collect all field errors into a map
        Map<String, String> errors = new HashMap<>();

        // ex.getBindingResult() = all validation results
        // getFieldErrors() = list of failed fields
        ex.getBindingResult()
                .getFieldErrors()
                .forEach((FieldError error) -> {
                    // error.getField() = which field failed
                    // "name", "price", "email"
                    String field = error.getField();

                    // error.getDefaultMessage() = your message
                    // "Name cannot be blank"
                    String message = error.getDefaultMessage();

                    errors.put(field, message);
                    // { "name": "Name cannot be blank" }
                });

        ErrorResponse response = new ErrorResponse(
                400,
                "Validation Failed",
                "Input validation failed. Check errors.",
                errors,                    // field errors map
                request.getRequestURI()    // which URL
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(response);
    }

    // ─────────────────────────────────────────
    // 2. RESOURCE NOT FOUND
    // Returns 404 Not Found
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
                // "Product not found with id: '5'"
                request.getRequestURI()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(response);
    }

    // ─────────────────────────────────────────
    // 3. DUPLICATE RESOURCE
    // Returns 409 Conflict
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
    // 4. BAD REQUEST
    // Returns 400 Bad Request
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
    // 5. ILLEGAL ARGUMENT
    // Returns 400 Bad Request
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
    // 6. CATCH ALL - any other exception
    // Returns 500 Internal Server Error
    // ─────────────────────────────────────────
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleAllExceptions(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse response = new ErrorResponse(
                500,
                "Internal Server Error",
                // don't expose internal error details
                // to client in production!
                "Something went wrong. Please try again.",
                request.getRequestURI()
        );

        // log the real error for developers
        System.err.println("Unexpected error: "
                + ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}