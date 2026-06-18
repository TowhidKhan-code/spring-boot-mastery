package com.towhid.spring_mvc.day09.mvc.repository;

import com.towhid.spring_mvc.day09.mvc.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository
        extends JpaRepository<Course,Integer> {

    Optional<Course> findByName(String name);

    boolean existsByName(String name);
}
