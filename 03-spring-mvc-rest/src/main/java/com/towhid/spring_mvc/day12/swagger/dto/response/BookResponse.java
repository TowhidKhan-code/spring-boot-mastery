package com.towhid.spring_mvc.day12.swagger.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Book response object")
public class BookResponse {

    @Schema(description = "Book ID", example = "1")
    private Integer id;

    @Schema(description = "Title", example = "Spring Boot in Action")
    private String title;

    @Schema(description = "Author", example = "Craig Walls")
    private String author;

    @Schema(description = "ISBN", example = "9781617292545")
    private String isbn;

    @Schema(description = "Description")
    private String description;

    @Schema(description = "Price", example = "49.99")
    private Double price;

    @Schema(description = "Category", example = "Programming")
    private String category;

    @Schema(description = "Stock quantity", example = "100")
    private Integer stockQuantity;

    @Schema(description = "Availability status")
    private Boolean available;

    @Schema(description = "Creation timestamp")
    private LocalDateTime createdAt;
}