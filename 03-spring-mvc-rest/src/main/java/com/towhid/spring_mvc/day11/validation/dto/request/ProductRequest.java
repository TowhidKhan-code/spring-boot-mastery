package com.towhid.spring_mvc.day11.validation.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Product name cannot be blank")
    // @NotBlank = not null AND not empty AND not whitespace
    // message = what to show when validation fails
    @Size(min = 2, max = 200,
            message = "Name must be between 2 and 200 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 500,
            message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Price is required")
    // @NotNull = cannot be null
    @Positive(message = "Price must be greater than zero")
    // @Positive = must be > 0
    // -5 ❌, 0 ❌, 0.01 ✅, 999 ✅
    private Double price;

    @Max(value = 100000,
            message = "Stock cannot exceed 100,000")
    private Integer stockQuantity;

    @NotBlank(message = "Category is required")
    @Size(max = 100,
            message = "Category cannot exceed 100 characters")
    private String category;

    @Pattern(
            regexp = "^(https?://).+",
            message = "Image URL must start with http:// or https://"
    )
    // ^ - starts with http
    // s? - zero or one
    // :// - must match
    // .+ - 1 or more required
    // @Pattern = must match regex
    // regexp = the regex pattern
    // this is OPTIONAL (no @NotBlank)
    // if provided must match pattern
    private String imageUrl;
}
