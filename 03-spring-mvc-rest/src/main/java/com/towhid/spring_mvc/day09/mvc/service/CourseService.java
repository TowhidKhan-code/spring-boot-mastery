package com.towhid.spring_mvc.day09.mvc.service;

import com.towhid.spring_mvc.day09.mvc.dto.CourseRequest;
import com.towhid.spring_mvc.day09.mvc.dto.CourseResponse;
import com.towhid.spring_mvc.day09.mvc.entity.Course;
import com.towhid.spring_mvc.day09.mvc.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {
    
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    
    private CourseResponse toResponse(Course course){
        CourseResponse response = new CourseResponse();
        response.setId(course.getId());
        response.setName(course.getName());
        response.setDescription(course.getDescription());
        response.setCreatedAt(course.getCreatedAt());
        return response;
    }
    
    private Course toEntity(CourseRequest request){
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        return course;
    }
    
    public CourseResponse createCourse(CourseRequest request){
        if(courseRepository.existsByName(request.getName())){
            throw new RuntimeException("Course already exists with name: " + request.getName());
        }
        Course course = toEntity(request);
        
        Course saved = courseRepository.save(course);
        
        return toResponse(saved);
    }
    
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses(){
        return courseRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Integer id){
        Course course = courseRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Course Not Found: "+ id));
        return toResponse(course);
    }
    
    public void deleteCourse(Integer id){
        courseRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Course Not Found: "+ id));
        courseRepository.deleteById(id);
    }
    
}
