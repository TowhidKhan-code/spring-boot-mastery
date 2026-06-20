package com.towhid.spring_mvc.day11.validation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {

    private boolean success;
    private String message;
    private T data;
    private LocalDateTime timestamp;

    // success WITH data
    public static <T> ApiResponse<T> success(
            String message, T data) {
        return new ApiResponse<>(
                true,
                message,
                data,
                LocalDateTime.now()
        );
    }

    // success WITHOUT data
    public static <T> ApiResponse<T> success(
            String message) {
        return new ApiResponse<>(
                true,
                message,
                null,
                LocalDateTime.now()
        );
    }

    // error
    public static <T> ApiResponse<T> error(
            String message) {
        return new ApiResponse<>(
                false,
                message,
                null,
                LocalDateTime.now()
        );
    }
}