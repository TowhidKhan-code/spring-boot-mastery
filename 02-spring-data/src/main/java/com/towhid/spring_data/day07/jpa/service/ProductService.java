package com.towhid.spring_data.day07.jpa.service;

import com.towhid.spring_data.day07.jpa.entity.Product;
import com.towhid.spring_data.day07.jpa.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    public Product createProduct(String name,String description,
                                 Double price,Integer stock,
                                 String category){
        // Check if product with same name exists
        if(productRepository.existsByName(name)){
            throw new RuntimeException(
                    "Product already exists: " + name);
        }
        Product product = new Product(name,description,price,stock,category);

        // save() = INSERT if new, UPDATE if existing
        // Hibernate generates INSERT SQL automatically
        Product saved = productRepository.save(product);
        System.out.println("Product saved with ID: " + saved.getId());
        return saved;
    }


    // ─────────────────────────────────────────
    // READ - single
    // ─────────────────────────────────────────
    public Product getById(Integer id){
        return productRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Product not found: " + id));
    }

    // ─────────────────────────────────────────
    // READ - all
    // ─────────────────────────────────────────
    public List<Product> getAll() {
        // findAll() → SELECT * FROM products
        return productRepository.findAll();
    }

    // ─────────────────────────────────────────
    // READ - by category
    // ─────────────────────────────────────────
    public List<Product> getByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    // ─────────────────────────────────────────
    // READ - by price range
    // ─────────────────────────────────────────
    public List<Product> getByPriceRange(
            Double min, Double max) {
        return productRepository.findByPriceBetween(min, max);
    }

    // ─────────────────────────────────────────
    // READ - search by name
    // ─────────────────────────────────────────
    public List<Product> searchByName(String keyword) {
        return productRepository.findByNameContaining(keyword);
    }

    // ─────────────────────────────────────────
    // READ - low stock
    // ─────────────────────────────────────────
    public List<Product> getLowStockProducts(Integer threshold) {
        return productRepository.findLowStockProducts(threshold);
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    public Product updateProduct(Integer id,String name,
                                 String description, Double price,
                                 Integer stock,String category){
        // Get existing product (throws if not found)
        Product existing = getById(id);
        // Update fields
        existing.setName(name);
        existing.setDescription(description);
        existing.setPrice(price);
        existing.setStockQuantity(stock);
        existing.setCategory(category);

        // save() on existing entity = UPDATE
        // Hibernate knows it's existing because id is set
        Product updated = productRepository.save(existing);
        System.out.println(" Product updated: " + id);
        return updated;
    }

    // ─────────────────────────────────────────
    // UPDATE STATUS (using @Query)
    // ─────────────────────────────────────────
    @Transactional
    // @Transactional required for @Modifying queries
    public void updateStatus(Integer id, Boolean status) {
        int rows = productRepository
                .updateProductStatus(id, status);
        if (rows == 0) {
            throw new RuntimeException(
                    "Product not found: " + id);
        }
        System.out.println(" Status updated for: " + id);
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    public void deleteProduct(Integer id) {
        // Check exists first
        Product product = getById(id);

        // deleteById → DELETE FROM products WHERE id = ?
        productRepository.deleteById(id);
        System.out.println(" Product deleted: " + id);
    }

    // ─────────────────────────────────────────
    // STATS
    // ─────────────────────────────────────────
    public void printStats() {
        long total = productRepository.count();
        long electronics = productRepository
                .countByCategory("Electronics");
        long active = productRepository
                .findByActive(true).size();

        System.out.println(" Total products   : " + total);
        System.out.println(" Electronics      : " + electronics);
        System.out.println(" Active products  : " + active);
    }
}
