package com.towhid.spring_data.day08.relationships.repository;

import com.towhid.spring_data.day08.relationships.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository
        extends JpaRepository<Tag,Integer> {
    Optional<Tag> findByName(String name);
    boolean existsByName(String name);
    // Load tag WITH all its products
    @Query("SELECT t FROM Tag t " +
            "LEFT JOIN FETCH t.products " +
            "WHERE t.name = :name")
    Optional<Tag> findByNameWithProducts(
            @Param("name") String name);
}
