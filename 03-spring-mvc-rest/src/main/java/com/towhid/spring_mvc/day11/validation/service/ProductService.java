package com.towhid.spring_mvc.day11.validation.service;

import com.towhid.spring_mvc.day11.validation.dto.request.ProductRequest;
import com.towhid.spring_mvc.day11.validation.dto.response.ProductResponse;
import com.towhid.spring_mvc.day11.validation.entity.Product;
import com.towhid.spring_mvc.day11.validation.exception.BadRequestException;
import com.towhid.spring_mvc.day11.validation.exception.DuplicateResourceException;
import com.towhid.spring_mvc.day11.validation.exception.ResourceNotFoundException;
import com.towhid.spring_mvc.day11.validation.repository.ProductRepository;
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

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    public ProductResponse createProduct(
            ProductRequest request) {

        // check duplicate - throw custom exception
        if (productRepository.existsByName(
                request.getName())) {
            throw new DuplicateResourceException(
                    "Product already exists with name: '"
                            + request.getName() + "'"
            );
            // GlobalExceptionHandler catches this
            // returns 409 Conflict
        }

        // business validation
        if (request.getPrice() != null
                && request.getStockQuantity() != null
                && request.getPrice() > 50000
                && request.getStockQuantity() > 1000) {
            throw new BadRequestException(
                    "High-value products cannot have " +
                            "stock quantity above 1000"
            );
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setCategory(request.getCategory());
        product.setImageUrl(request.getImageUrl());

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

        // validation
        if (id <= 0) {
            throw new BadRequestException(
                    "ID must be a positive number"
            );
        }

        // throw custom exception if not found
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",  // resource name
                                "id",       // field name
                                id          // field value
                        )
                );
        // GlobalExceptionHandler catches this
        // returns 404 Not Found

        return toResponse(product);
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    public ProductResponse updateProduct(
            Integer id, ProductRequest request) {

        Product existing = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product", "id", id
                        )
                );

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setStockQuantity(
                request.getStockQuantity());
        existing.setCategory(request.getCategory());
        existing.setImageUrl(request.getImageUrl());

        return toResponse(productRepository.save(existing));
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    public void deleteProduct(Integer id) {
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product", "id", id
                        )
                );

        productRepository.deleteById(id);
    }
}
