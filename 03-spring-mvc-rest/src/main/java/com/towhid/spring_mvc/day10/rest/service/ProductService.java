package com.towhid.spring_mvc.day10.rest.service;

import com.towhid.spring_mvc.day10.rest.dto.request.ProductRequest;
import com.towhid.spring_mvc.day10.rest.dto.request.StockUpdateRequest;
import com.towhid.spring_mvc.day10.rest.dto.response.ProductResponse;
import com.towhid.spring_mvc.day10.rest.dto.response.ProductStats;
import com.towhid.spring_mvc.day10.rest.entity.Product;
import com.towhid.spring_mvc.day10.rest.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ─────────────────────────────────────────
    // MAPPER METHODS
    // ─────────────────────────────────────────

    // Entity → Response DTO
    private ProductResponse toResponse(Product product){
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setCategory(product.getCategory());
        response.setImageUrl(product.getImageUrl());
        response.setActive(product.getActive());
        response.setCreatedAt(product.getCreatedAt());
        return response;
    }

    // Request DTO → Entity
    private Product toEntity(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());
        return product;
    }

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    public ProductResponse createProduct(
            ProductRequest request) {

        // check duplicate name
        if (productRepository.existsByName(
                request.getName())) {
            throw new RuntimeException(
                    "Product already exists: "
                            + request.getName());
        }

        Product product = toEntity(request);
        Product saved = productRepository.save(product);
        return toResponse(saved);
    }

    // ─────────────────────────────────────────
    // GET ALL
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));
        return toResponse(product);
    }

    // ─────────────────────────────────────────
    // GET BY CATEGORY
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> getByCategory(
                String category) {
        return productRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // SEARCH BY NAME
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> searchByName(
            String keyword) {
        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // GET BY PRICE RANGE
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> getByPriceRange(
            Double min, Double max) {
        return productRepository
                .findByPriceBetween(min, max)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // GET LOW STOCK
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<ProductResponse> getLowStock(
            Integer threshold) {
        return productRepository
                .findLowStockProducts(threshold)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // UPDATE (PUT - replace all fields)
    // ─────────────────────────────────────────
    public ProductResponse updateProduct (
            Integer id,ProductRequest request) {
        Product existing = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));

        // replace ALL fields
        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStockQuantity(
                request.getStockQuantity());
        existing.setCategory(request.getCategory());
        existing.setImageUrl(request.getImageUrl());

        Product updated = productRepository.save(existing);
        return toResponse(updated);
    }

    // ─────────────────────────────────────────
    // UPDATE STOCK (PATCH - partial update)
    // ─────────────────────────────────────────
    public ProductResponse updateStock(
            Integer id,
            StockUpdateRequest request) {

        Product existing = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));

        // update ONLY stock quantity
        existing.setStockQuantity(
                request.getStockQuantity());

        Product updated = productRepository.save(existing);
        return toResponse(updated);
    }

    // ─────────────────────────────────────────
    // DEACTIVATE (soft delete)
    // ─────────────────────────────────────────
    public ProductResponse deactivateProduct(Integer id) {
        Product existing = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));

        // soft delete = don't actually delete
        // just mark as inactive
        existing.setActive(false);
        Product updated = productRepository.save(existing);
        return toResponse(updated);
    }

    // ─────────────────────────────────────────
    // DELETE (hard delete)
    // ─────────────────────────────────────────
    public void deleteProduct(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));

        productRepository.deleteById(id);
    }

    // ─────────────────────────────────────────
    // Practice Task 1 - Add endpoint:
    //  GET /api/products/active
    //      → returns only active products
    //      → wrapped in ApiResponse
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public  List<ProductResponse> getActiveProducts() {
        return productRepository
                .findByActive(true)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // Practice Task 2 - ProductStats (dto)
    //  → Add endpoint:
    //  GET /api/products/stats
    //    → returns:
    //      {
    //          "totalProducts": 10,
    //          "activeProducts": 8,
    //          "totalCategories": 3,
    //          "lowStockCount": 2
    //      }
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public ProductStats getProductStats() {

        long totalProducts = productRepository.count();
        long activeProducts = productRepository.countByActive(true);
        long totalCategories = productRepository.countDistinctCategories();
        long lowStockCount = productRepository.findLowStockProducts(10).size();

        return new ProductStats(
                totalProducts,
                activeProducts,
                totalCategories,
                lowStockCount
        );
    }


    // ─────────────────────────────────────────
    // Practice Task 3 - Add endpoint:
    //  PATCH /api/products/{id}/activate
    //      → reactivates a deactivated product
    //      → returns updated product
    //  And also add:
    //  GET /api/products/inactive
    //      → returns all inactive products
    // ─────────────────────────────────────────
    public ProductResponse activateProduct(
            Integer id){
        Product existing = productRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + id));

        existing.setActive(true);
        Product saved = productRepository.save(existing);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public  List<ProductResponse> getInactiveProducts() {
        return productRepository
                .findByActive(false)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
