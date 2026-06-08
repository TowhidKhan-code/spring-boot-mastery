package com.towhid.spring_data.day06.jdbc.service;

import com.towhid.spring_data.day06.jdbc.model.Course;
import com.towhid.spring_data.day06.jdbc.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course createCourse(String name,Integer durationWeeks){
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if(durationWeeks <= 0){
            throw new IllegalArgumentException("Duration cannot be zero or less");
        }
        Course course = new Course(name, durationWeeks);
        Course saved = courseRepository.save(course);
        return saved;
    }

    public Course getCourseById(Integer id){
        return courseRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Course not found with id: " + id)
                );
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public Course getCourseByName(String name){
        return courseRepository.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException("No course find with the name: " + name)
                );
    }

    public List<Course> getCourseWithDurations(Integer durationWeeks){
        return courseRepository.findByDuration(durationWeeks);
    }

    public Course updateCourse(Integer id,String name,Integer durationWeeks){
        Course existing = getCourseById(id);
        existing.setName(name);
        existing.setDurationWeeks(durationWeeks);

        boolean success = courseRepository.update(existing);
        if (!success){
            throw new RuntimeException("Update failed for id: " + id);
        }

        System.out.println("Course updated: " + id);
        return existing;
    }

    public void deleteCourse(Integer id){
        getCourseById(id);
        boolean success = courseRepository.deleteById(id);
        if (!success) {
            throw new RuntimeException("Delete failed for id: " + id);
        }
        System.out.println("Course deleted: " + id);
    }

    public void printStats() {
        int total = courseRepository.count();
        System.out.println("Total courses in DB: " + total);
    }

}
