package com.towhid.spring_mvc.day10.rest.controller;

import com.towhid.spring_mvc.day10.rest.dto.request.ProductRequest;
import com.towhid.spring_mvc.day10.rest.dto.request.StockUpdateRequest;
import com.towhid.spring_mvc.day10.rest.dto.response.ApiResponse;
import com.towhid.spring_mvc.day10.rest.dto.response.ProductResponse;
import com.towhid.spring_mvc.day10.rest.dto.response.ProductStats;
import com.towhid.spring_mvc.day10.rest.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>>
            createProduct(
            @RequestBody ProductRequest request) {
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
    // GET ALL — GET /api/products
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
    // GET BY ID — GET /api/products/{id}
    // ─────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>>
            getProductById(@PathVariable Integer id) {

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
    // GET BY CATEGORY
    // GET /api/products/category?name=Electronics
    // ─────────────────────────────────────────
    @GetMapping("/category")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
            getByCategory(@RequestParam String name) {

        List<ProductResponse> products =
                productService.getByCategory(name);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products in category: " + name,
                        products
                )
        );
    }

    // ─────────────────────────────────────────
    // SEARCH — GET /api/products/search?keyword=phone
    // ─────────────────────────────────────────
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
    searchProducts(
            @RequestParam String keyword) {

        List<ProductResponse> products =
                productService.searchByName(keyword);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Search results for: " + keyword,
                        products
                )
        );
    }

    // ─────────────────────────────────────────
    // PRICE RANGE
    // GET /api/products/price-range?min=100&max=500
    // ─────────────────────────────────────────
    @GetMapping("/price-range")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
    getByPriceRange(
            @RequestParam Double min,
            @RequestParam Double max) {

        List<ProductResponse> products =
                productService.getByPriceRange(min, max);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products between $" + min
                                + " and $" + max,
                        products
                )
        );
    }

    // ─────────────────────────────────────────
    // LOW STOCK
    // GET /api/products/low-stock?threshold=10
    // ─────────────────────────────────────────
    @GetMapping("/low-stock")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
    getLowStock(
            @RequestParam(
                    defaultValue = "10"
            ) Integer threshold) {
        // defaultValue = if ?threshold not provided
        // use 10 as default

        List<ProductResponse> products =
                productService.getLowStock(threshold);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Low stock products (≤" + threshold + ")",
                        products
                )
        );
    }

    // ─────────────────────────────────────────
    // UPDATE — PUT /api/products/{id}
    // ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>>
    updateProduct(
            @PathVariable Integer id,
            @RequestBody ProductRequest request) {

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
    // UPDATE STOCK — PATCH /api/products/{id}/stock
    // ─────────────────────────────────────────
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ApiResponse<ProductResponse>>
    updateStock(
            @PathVariable Integer id,
            @RequestBody StockUpdateRequest request) {

        ProductResponse product =
                productService.updateStock(id, request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Stock updated successfully",
                        product
                )
        );
    }

    // ─────────────────────────────────────────
    // DEACTIVATE — PATCH /api/products/{id}/deactivate
    // ─────────────────────────────────────────
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ApiResponse<ProductResponse>>
    deactivateProduct(
            @PathVariable Integer id) {

        ProductResponse product =
                productService.deactivateProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deactivated successfully",
                        product
                )
        );
    }

    // ─────────────────────────────────────────
    // DELETE — DELETE /api/products/{id}
    // ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>>
    deleteProduct(
            @PathVariable Integer id) {

        productService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully"
                )
        );
    }

    // ─────────────────────────────────────────
    // Practice Task 1
    // ─────────────────────────────────────────
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
            getActiveProducts() {
        List<ProductResponse> products =
                productService.getActiveProducts();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Active products retrieved successfully",
                        products
                )
        );
    }

    // Practice Task 2
    @GetMapping("/stats")
    public ResponseEntity<ApiResponse<ProductStats>>
    getProductStats() {

        ProductStats stats = productService.getProductStats();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product statistics retrieved successfully",
                        stats
                )
        );
    }

    // ─────────────────────────────────────────
    // Practice Task 3
    // ─────────────────────────────────────────
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ApiResponse<ProductResponse>>
            activateProduct(@PathVariable Integer id){
        ProductResponse product =
                productService.activateProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product activated successfully",
                        product
                )
        );
    }

    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<ProductResponse>>>
            getInactiveProducts() {
        List<ProductResponse> products =
                productService.getInactiveProducts();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Inactive products retrieved successfully",
                        products
                )
        );
    }
}
