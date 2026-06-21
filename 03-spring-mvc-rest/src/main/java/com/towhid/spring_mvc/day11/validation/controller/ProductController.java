package com.towhid.spring_mvc.day11.validation.controller;

import com.towhid.spring_mvc.day11.validation.dto.request.ProductRequest;
import com.towhid.spring_mvc.day11.validation.dto.response.ApiResponse;
import com.towhid.spring_mvc.day11.validation.dto.response.ProductResponse;
import com.towhid.spring_mvc.day11.validation.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated // ← IMPORTANT for @PathVariable and @RequestParam validation
@RestController
@RequestMapping("/api/v1/products")
// v1 = version 1 of our API
// Good practice to version your APIs!
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>>
            createProduct(
            // @Valid = TRIGGER validation
            // Without @Valid → annotations ignored!
            // With @Valid → Spring validates request
            // If fails → MethodArgumentNotValidException
            // → GlobalExceptionHandler catches it!
                    @Valid @RequestBody ProductRequest request) {

        ProductResponse product =
                productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(
                        "Product created successfully",
                        product
                ));
    }

    // ─────────────────────────────────────────
    // GET ALL
    // ─────────────────────────────────────────
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
    getAllProducts() {

        List<ProductResponse> products =
                productService.getAllProducts();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products retrieved successfully",
                        products
                )
        );
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>>
    getProductById(
            @PathVariable
            @Min(value = 1, message = "ID must be positive")
            Integer id) {

        // if not found → ResourceNotFoundException
        // → GlobalExceptionHandler → 404 response
        ProductResponse product =
                productService.getProductById(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product retrieved successfully",
                        product
                )
        );
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>>
    updateProduct(
            @PathVariable
            @Min(value = 1, message = "ID must be positive")
            Integer id,
            @Valid @RequestBody ProductRequest request) {

        ProductResponse product =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product updated successfully",
                        product
                )
        );
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>
    deleteProduct(
            @PathVariable
            @Min(value = 1, message = "ID must be positive")
            Integer id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully"
                )
        );
    }

    // Practice Task 2
    @GetMapping("/{id}/check-stock")
    public ResponseEntity<ApiResponse<String>>
    checkStock(
            @PathVariable
            @Min(value = 1, message = "ID must be positive")
            Integer id,

            @RequestParam
            @Min(value = 1, message = "Quantity must be positive")
            Integer quantity) {

        productService.checkStock(id, quantity);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock is sufficient"
                )
        );
    }
}
