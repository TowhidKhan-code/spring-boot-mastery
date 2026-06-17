package com.towhid.spring_data.day08.relationships.service;

import com.towhid.spring_data.day07.jpa.entity.Product;
import com.towhid.spring_data.day07.jpa.repository.ProductRepository;
import com.towhid.spring_data.day08.relationships.entity.Tag;
import com.towhid.spring_data.day08.relationships.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductTagService {

    private final ProductRepository productRepository;
    private final TagRepository tagRepository;

    public ProductTagService(
            ProductRepository productRepository,
            TagRepository tagRepository) {
        this.productRepository = productRepository;
        this.tagRepository = tagRepository;
    }

    // ─────────────────────────────────────────
    // 1. TAG A PRODUCT
    // ─────────────────────────────────────────
    public void tagProduct(Integer productId, String tagName) {

        // Step 1: Find product
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + productId));

        // Step 2: Find tag or create new one
        Tag tag = tagRepository.findByName(tagName)
                .orElseGet(() -> {
                    Tag newTag = new Tag(tagName);
                    return tagRepository.save(newTag);
                });

        // Step 3: Check if already tagged
        if (product.getTags().contains(tag)) {
            System.out.println("⚠️ Product already has tag: "
                    + tagName);
            return;
        }

        // Step 4: Add tag to product
        product.addTag(tag);

        // Step 5: Save product
        productRepository.save(product);

        System.out.println("✅ Tagged product '"
                + product.getName() + "' with: " + tagName);
    }

    // ─────────────────────────────────────────
    // 2. REMOVE TAG FROM PRODUCT
    // ─────────────────────────────────────────
    public void removeTag(Integer productId, String tagName) {

        // Step 1: Find product with tags loaded
        Product product = productRepository
                .findByIdWithTags(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + productId));

        // Step 2: Find tag
        Tag tag = tagRepository.findByName(tagName)
                .orElseThrow(() -> new RuntimeException(
                        "Tag not found: " + tagName));

        // Step 3: Check if product has this tag
        if (!product.getTags().contains(tag)) {
            System.out.println("⚠️ Product doesn't have tag: "
                    + tagName);
            return;
        }

        // Step 4: Remove tag from product
        product.removeTag(tag);

        // Step 5: Save product
        productRepository.save(product);

        System.out.println("✅ Removed tag '" + tagName
                + "' from product: " + product.getName());
    }

    // ─────────────────────────────────────────
    // 3. GET PRODUCT WITH ALL TAGS
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public Product getProductWithTags(Integer productId) {

        return productRepository.findByIdWithTags(productId)
                .orElseThrow(() -> new RuntimeException(
                        "Product not found: " + productId));
    }

    // ─────────────────────────────────────────
    // 4. GET TAG WITH ALL PRODUCTS
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public Tag getTagWithProducts(String tagName) {

        return tagRepository.findByNameWithProducts(tagName)
                .orElseThrow(() -> new RuntimeException(
                        "Tag not found: " + tagName));
    }

    // ─────────────────────────────────────────
    // 5. GET ALL TAGS OF A PRODUCT (helper)
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public void printProductTags(Integer productId) {

        Product product = getProductWithTags(productId);

        System.out.println("\nTags for product: "
                + product.getName());

        if (product.getTags().isEmpty()) {
            System.out.println("  (no tags)");
        } else {
            product.getTags().forEach(tag ->
                    System.out.println("  → " + tag.getName())
            );
        }
    }

    // ─────────────────────────────────────────
    // 6. GET ALL PRODUCTS WITH A TAG (helper)
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public void printTagProducts(String tagName) {

        Tag tag = getTagWithProducts(tagName);

        System.out.println("\nProducts with tag: "
                + tag.getName());

        if (tag.getProducts().isEmpty()) {
            System.out.println("  (no products)");
        } else {
            tag.getProducts().forEach(product ->
                    System.out.println("  → " + product.getName())
            );
        }
    }
}