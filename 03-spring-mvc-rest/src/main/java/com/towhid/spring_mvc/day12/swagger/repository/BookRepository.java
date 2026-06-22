package com.towhid.spring_mvc.day12.swagger.repository;

import com.towhid.spring_mvc.day12.swagger.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository
        extends JpaRepository<Book,Integer> {

    // Check ISBN exists
    boolean existsByIsbn(String isbn);

    // Paginated find by category
    // Just add Pageable parameter!
    // Spring handles pagination automatically!
    Page<Book> findByCategory(
            String category, Pageable pageable);

    // Paginated find by available
    Page<Book> findByAvailable(
            Boolean available, Pageable pageable);

    // Paginated search by title or author
    @Query("SELECT b FROM Book b WHERE " +
            "LOWER(b.title) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(b.author) LIKE LOWER(CONCAT('%',:keyword,'%'))")
    Page<Book> searchBooks(
            @Param("keyword")String keyword,
            Pageable pageable);
    // Notice: same JPQL but returns Page<Book>
    // and accepts Pageable!

    // Paginated find by price range
    Page<Book> findByPriceBetween(
            Double min, Double max, Pageable pageable);

}
