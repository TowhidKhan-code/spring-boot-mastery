package com.towhid.spring_mvc.day12.swagger.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Schema describes fields in Swagger docs
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating or updating a book")
public class BookRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200,
            message = "Title must be between 1 and 200 characters")
    @Schema(
            description = "Title of the book",
            example = "Spring Boot in Action"
    )
    // @Schema → shows example value in Swagger UI
    // makes testing easy! Fill-in values ready!
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 100,
            message = "Author name cannot exceed 100 characters")
    @Schema(
            description = "Author of the book",
            example = "Craig Walls"
    )
    private String author;

    @NotBlank(message = "ISBN is required")
    @Pattern(
            regexp = "^[0-9]{13}$",
            message = "ISBN must be exactly 13 digits"
    )
    @Schema(
            description = "13-digit ISBN number",
            example = "9781617292545"
    )
    private String isbn;

    @Size(max = 1000,
            message = "Description cannot exceed 1000 characters")
    @Schema(
            description = "Book description",
            example = "A comprehensive guide to Spring Boot"
    )
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    @Schema(
            description = "Price of the book",
            example = "49.99"
    )
    private Double price;

    @NotBlank(message = "Category is required")
    @Schema(
            description = "Book category",
            example = "Programming"
    )
    private String category;

    @NotNull(message = "Stock quantity is required")
    @PositiveOrZero(message = "Stock cannot be negative")
    @Schema(
            description = "Number of books in stock",
            example = "100"
    )
    private Integer stockQuantity;
}