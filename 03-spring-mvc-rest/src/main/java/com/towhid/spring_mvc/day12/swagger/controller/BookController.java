package com.towhid.spring_mvc.day12.swagger.controller;

import com.towhid.spring_mvc.day12.swagger.dto.request.BookRequest;
import com.towhid.spring_mvc.day12.swagger.dto.response.ApiResponse;
import com.towhid.spring_mvc.day12.swagger.dto.response.BookResponse;
import com.towhid.spring_mvc.day12.swagger.dto.response.PagedResponse;
import com.towhid.spring_mvc.day12.swagger.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// @Tag groups endpoints in Swagger UI
// Like a section heading in the docs
@Tag(name = "Books", description = "Book management APIs")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    @Operation(
            summary = "Create a new book",
            description = "Creates a new book with the provided details"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Book created successfully"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>>
    createBook(
            @Valid @RequestBody BookRequest request) {

        BookResponse book = bookService.createBook(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Book created successfully", book));
    }

    // ─────────────────────────────────────────
    // GET ALL — PAGINATED!
    // ─────────────────────────────────────────
    @Operation(
            summary = "Get all books (paginated)",
            description = "Returns paginated list of books " +
                    "with sorting options"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<BookResponse>>>
    getAllBooks(
            // @RequestParam with defaultValue
            // so user doesn't HAVE to provide them
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Items per page")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort by field name")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Sort direction: asc or desc")
            @RequestParam(defaultValue = "asc") String direction) {

        PagedResponse<BookResponse> books =
                bookService.getAllBooks(
                        page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Books retrieved successfully", books));
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────
    @Operation(
            summary = "Get book by ID",
            description = "Returns a single book by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>>
    getBookById(
            @Parameter(description = "Book ID")
            @PathVariable Integer id) {

        BookResponse book = bookService.getBookById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Book retrieved successfully", book));
    }

    // ─────────────────────────────────────────
    // GET BY CATEGORY — PAGINATED
    // ─────────────────────────────────────────
    @Operation(
            summary = "Get books by category (paginated)",
            description = "Returns paginated books " +
                    "filtered by category"
    )
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<PagedResponse<BookResponse>>>
    getByCategory(
            @Parameter(description = "Category name")
            @RequestParam String name,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PagedResponse<BookResponse> books =
                bookService.getByCategory(
                        name, page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Books in category: " + name, books));
    }

    // ─────────────────────────────────────────
    // SEARCH — PAGINATED
    // ─────────────────────────────────────────
    @Operation(
            summary = "Search books (paginated)",
            description = "Search books by title or author name"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<PagedResponse<BookResponse>>>
    searchBooks(
            @Parameter(description = "Search keyword")
            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PagedResponse<BookResponse> books =
                bookService.searchBooks(
                        keyword, page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Search results for: " + keyword, books));
    }

    // ─────────────────────────────────────────
    // PRICE RANGE — PAGINATED
    // ─────────────────────────────────────────
    @Operation(
            summary = "Get books by price range (paginated)",
            description = "Returns books within a price range"
    )
    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<PagedResponse<BookResponse>>>
    getByPriceRange(
            @Parameter(description = "Minimum price")
            @RequestParam Double min,

            @Parameter(description = "Maximum price")
            @RequestParam Double max,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "price") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        PagedResponse<BookResponse> books =
                bookService.getByPriceRange(
                        min, max, page, size, sortBy, direction);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Books between $" + min + " and $" + max,
                        books));
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    @Operation(
            summary = "Update a book",
            description = "Updates an existing book by ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>>
    updateBook(
            @PathVariable Integer id,
            @Valid @RequestBody BookRequest request) {

        BookResponse book =
                bookService.updateBook(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Book updated successfully", book));
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    @Operation(
            summary = "Delete a book",
            description = "Deletes a book by ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>
    deleteBook(
            @PathVariable Integer id) {

        bookService.deleteBook(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Book deleted successfully"));
    }
}