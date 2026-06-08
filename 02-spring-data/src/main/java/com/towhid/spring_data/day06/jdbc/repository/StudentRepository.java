package com.towhid.spring_data.day06.jdbc.repository;

import com.towhid.spring_data.day06.jdbc.model.Student;

import java.util.List;
import java.util.Optional;

// Interface defines WHAT operations we can do
// Implementation defines HOW we do them
// This is good design - separation of concerns
public interface StudentRepository {

    // CREATE
    Student save(Student student);

    // READ - single
    Optional<Student> findById(Integer id);
    // Optional = might return Student or might be empty
    // Safer than returning null!

    // READ - all
    List<Student> findAll();

    // READ - by course
    List<Student> findByCourse(String course);

    // UPDATE
    boolean update(Student student);
    // returns true if update was successful

    // DELETE
    boolean deleteById(Integer id);
    // returns true if delete was successful

    // COUNT
    int count();
    // total number of students
}