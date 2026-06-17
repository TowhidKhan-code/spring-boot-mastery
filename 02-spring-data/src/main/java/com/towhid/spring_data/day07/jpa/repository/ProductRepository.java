package com.towhid.spring_data.day07.jpa.repository;

import com.towhid.spring_data.day07.jpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

// JpaRepository<Entity, ID type>
// Just by extending this interface Spring gives you:
// save(), findById(), findAll(), delete(),
// count(), existsById() and more!
// You write ZERO implementation code!
@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    // ─────────────────────────────────────────
    // DERIVED QUERY METHODS
    // Spring reads the METHOD NAME and generates SQL!
    // ─────────────────────────────────────────

    // Method name: findBy + Category
    // Generated SQL: SELECT * FROM products WHERE category = ?
    List<Product> findByCategory(String category);

    // findBy + Active
    // SELECT * FROM products WHERE is_active = ?
    List<Product> findByActive(Boolean active);

    // findBy + PriceLessThan
    // SELECT * FROM products WHERE price < ?
    List<Product> findByPriceLessThan(Double price);

    // findBy + PriceGreaterThan
    // SELECT * FROM products WHERE price > ?
    List<Product> findByPriceGreaterThan(Double price);

    // findBy + PriceBetween
    // SELECT * FROM products WHERE price BETWEEN ? AND ?
    List<Product> findByPriceBetween(Double min, Double max);

    // findBy + NameContaining
    // SELECT * FROM products WHERE name LIKE '%?%'
    List<Product> findByNameContaining(String keyword);

    // findBy + Category + Active (AND condition)
    // SELECT * FROM products
    // WHERE category = ? AND is_active = ?
    List<Product> findByCategoryAndActive(
            String category, Boolean active);

    // findBy + Category + OrderBy + Price + Asc
    // SELECT * FROM products
    // WHERE category = ? ORDER BY price ASC
    List<Product> findByCategoryOrderByPriceAsc(String category);

    // existsBy + Name
    // SELECT COUNT(*) > 0 FROM products WHERE name = ?
    boolean existsByName(String name);

    // countBy + Category
    // SELECT COUNT(*) FROM products WHERE category = ?
    long countByCategory(String category);

    // ─────────────────────────────────────────
    // JPQL QUERIES (@Query)
    // When derived methods are not enough
    // JPQL uses CLASS names not TABLE names
    // ─────────────────────────────────────────

    // 'Product' = Java class name (not table name)
    // 'p.category' = Java field name (not column name)
    @Query("SELECT p FROM Product p WHERE p.category = :category")
    List<Product> getByCategory(@Param("category") String category);
    // :category = named parameter
    // @Param("category") maps method param to :category

    // JPQL with calculation
    @Query("SELECT p FROM Product p WHERE p.price * :discount < :maxPrice")
    List<Product> findAffordableProducts(
            @Param("discount") Double discount,
            @Param("maxPrice") Double maxPrice);

    // Native SQL query (actual MySQL SQL)
    // nativeQuery = true → use real SQL not JPQL
    @Query(value = "SELECT * FROM products WHERE stock_quantity < :threshold",
            nativeQuery = true)
    List<Product> findLowStockProducts(@Param("threshold") Integer threshold);

    // JPQL Update query
    // @Modifying = this query modifies data
    // (required for UPDATE/DELETE in @Query)
    @Modifying
    @Query("UPDATE Product p SET p.active = :status WHERE p.id = :id")
    int updateProductStatus(
            @Param("id") Integer id,
            @Param("status") Boolean status);

    // Practice task
    List<Product> findByStockQuantityGreaterThan(Integer value);

    List<Product> findByActiveOrderByPriceDesc(Boolean active);

    long countByActive(Boolean active);

    @Query("SELECT p FROM Product p WHERE p.price < " +
            "(SELECT AVG(p2.price) FROM Product p2)")
    List<Product> findBelowAveragePrice();

    // Load product WITH all its tags
    @Query("SELECT p FROM Product p " +
            "LEFT JOIN FETCH p.tags " +
            "WHERE p.id = :id")
    Optional<Product> findByIdWithTags(
            @Param("id") Integer id);
}
