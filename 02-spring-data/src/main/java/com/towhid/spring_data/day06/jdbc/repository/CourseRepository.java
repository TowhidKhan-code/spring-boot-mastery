package com.towhid.spring_data.day06.jdbc.repository;

import com.towhid.spring_data.day06.jdbc.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    Course save(Course course);

    Optional<Course> findById(Integer id);

    List<Course> findAll();

    Optional<Course> findByName(String name);

    List<Course> findByDuration(Integer duration);

    boolean update(Course course);

    boolean deleteById(Integer id);

    int count();
}
