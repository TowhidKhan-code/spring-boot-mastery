package com.towhid.spring_mvc.day11.validation.repository;

import com.towhid.spring_mvc.day11.validation.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Integer> {

    // check if name exists
    boolean existsByName(String name);

    // find by category
    List<Product> findByCategory(String category);

    // find active products
    List<Product> findByActive(Boolean active);

    // search by name
    List<Product> findByNameContainingIgnoreCase(
            String keyword);
}