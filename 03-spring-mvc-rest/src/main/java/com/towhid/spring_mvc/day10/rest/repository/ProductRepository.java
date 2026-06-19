package com.towhid.spring_mvc.day10.rest.repository;

import com.towhid.spring_mvc.day10.rest.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository
        extends JpaRepository<Product, Integer> {

    // find by category
    List<Product> findByCategory(String category);

    // find active products
    List<Product> findByActive(Boolean active);

    // find by category and active
    List<Product> findByCategoryAndActive(
            String category, Boolean active);

    // search by name containing keyword
    List<Product> findByNameContainingIgnoreCase(
            String keyword);
    // IgnoreCase = case-insensitive search
    // "iphone" matches "iPhone" matches "IPHONE"

    // find by price range
    List<Product> findByPriceBetween(
            Double min, Double max);

    // check name exists
    boolean existsByName(String name);

    // find low stock
    @Query("SELECT p FROM Product p " +
            "WHERE p.stockQuantity <= :threshold " +
            "AND p.active = true " +
            "ORDER BY p.stockQuantity ASC")
    List<Product> findLowStockProducts(
            @Param("threshold") Integer threshold);

    // find by category ordered by price
    List<Product> findByCategoryOrderByPriceAsc(
            String category);
    //----------------------------------------
    // Practice Task 2
    //----------------------------------------

    // Count active products
    long countByActive(Boolean active);

    // Find distinct categories
//    List<Product> findDistinctByCategoryIsNotNull();

    // OR better with custom query:
    @Query("SELECT COUNT(DISTINCT p.category) FROM Product p WHERE p.category IS NOT NULL")
    long countDistinctCategories();
}