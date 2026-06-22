package com.towhid.spring_mvc.day12.swagger.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// Wraps paginated results with metadata
// Client knows: total pages, current page,
// has next page etc.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response wrapper")
public class PagedResponse<T> {

    @Schema(description = "List of items on current page")
    private List<T> content;
    // the actual items for this page

    @Schema(description = "Current page number", example = "0")
    private int pageNumber;
    // which page we are on (0-based)

    @Schema(description = "Items per page", example = "10")
    private int pageSize;
    // how many items per page

    @Schema(description = "Total number of items", example = "100")
    private long totalElements;
    // total items across ALL pages

    @Schema(description = "Total number of pages", example = "10")
    private int totalPages;
    // total number of pages

    @Schema(description = "Is this the first page?")
    private boolean first;
    // true if this is page 0

    @Schema(description = "Is this the last page?")
    private boolean last;
    // true if this is the final page

    @Schema(description = "Does a next page exist?")
    private boolean hasNext;

    @Schema(description = "Does a previous page exist?")
    private boolean hasPrevious;
}