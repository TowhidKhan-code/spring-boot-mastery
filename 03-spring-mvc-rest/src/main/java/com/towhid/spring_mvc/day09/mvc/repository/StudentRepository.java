package com.towhid.spring_mvc.day09.mvc.repository;

import com.towhid.spring_mvc.day09.mvc.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository
        extends JpaRepository<Student, Integer> {

    // find by email
    Optional<Student> findByEmail(String email);

    // check email exists
    boolean existsByEmail(String email);

    // find by course
    List<Student> findByCourse(String course);

    // find by grade greater than
    List<Student> findByGradeGreaterThan(Double grade);

}