package com.towhid.spring_mvc.day10.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// This wraps EVERY response from our API
// Consistent structure for ALL endpoints
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
// <T> = Generic type
// T can be anything:
// ApiResponse<ProductResponse>
// ApiResponse<List<ProductResponse>>
// ApiResponse<String>

    private boolean success;
    // true = request worked
    // false = something went wrong

    private String message;
    // human-readable message
    // "Product created successfully"
    // "Product not found"

    private T data;
    // the actual data
    // can be any type (T)

    private LocalDateTime timestamp;
    // when this response was created

    // ─────────────────────────────────────────
    // STATIC FACTORY METHODS
    // Easy ways to create responses
    // ─────────────────────────────────────────

    // Success response WITH data
    public static <T> ApiResponse<T> success(
            String message, T data){
        return new ApiResponse<>(
                true,        // success = true
                message,     // your message
                data,        // your data
                LocalDateTime.now() // current time
        );
    }

    // Success response WITHOUT data
    public static <T> ApiResponse<T> success(
            String message) {
        return new ApiResponse<>(
                true,
                message,
                null,        // no data
                LocalDateTime.now()
        );
    }

    // Error response
    public static <T> ApiResponse<T> error(
            String message) {
        return new ApiResponse<>(
                false,       // success = false
                message,     // error message
                null,        // no data on error
                LocalDateTime.now()
        );
    }
}
