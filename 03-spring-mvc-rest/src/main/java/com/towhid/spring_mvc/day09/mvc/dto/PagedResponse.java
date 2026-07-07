// ------------------------------------------------------
// DAY 12
// TASK 1 - Add Swagger annotations to your StudentController from Day 9:
//      @Tag on the class
//      @Operation on each method
//      @Parameter on path variables and request params

// Response :
// UPDATED StudentRequest,StudentResponse(Added @Schema)
// Updated StudentController (Added Swagger Annotations)
// ------------------------------------------------------

// ------------------------------------------------------
// TASK 2 - Add Pagination to StudentController :
//      GET /api/students should accept page, size, sortBy, direction
//      Create PagedResponse and return paginated students
// ------------------------------------------------------

// ------------------------------------------------------
// TASK 3 - Add Swagger + Pagination to your Employee Management System:
//      Both DepartmentController and EmployeeController
//      Swagger docs at /swagger-ui.html
//      Paginated employee list
// ------------------------------------------------------

package com.towhid.spring_mvc.day09.mvc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Paginated response wrapper")
public class PagedResponse<T> {

    @Schema(description = "List of items on current page")
    private List<T> content;

    @Schema(description = "Current page number", example = "0")
    private int pageNumber;

    @Schema(description = "Items per page", example = "10")
    private int pageSize;

    @Schema(description = "Total number of items", example = "100")
    private long totalElements;

    @Schema(description = "Total number of pages", example = "10")
    private int totalPages;

    @Schema(description = "Is this the first page?")
    private boolean first;

    @Schema(description = "Is this the last page?")
    private boolean last;

    @Schema(description = "Does a next page exist?")
    private boolean hasNext;

    @Schema(description = "Does a previous page exist?")
    private boolean hasPrevious;
}