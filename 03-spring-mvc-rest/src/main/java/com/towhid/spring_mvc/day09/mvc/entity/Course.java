// ─────────────────────────────────────────
// Practice Task 3 -
// Create a completely separate CourseController with:
//  - GET    /api/courses          → list all courses
//  - POST   /api/courses          → create course
//  - GET    /api/courses/{id}     → get by id
//  - DELETE /api/courses/{id}     → delete course
// With its own Entity, Repository, Service, DTO and Controller.
// ─────────────────────────────────────────

package com.towhid.spring_mvc.day09.mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="course_name",unique = true,nullable = false)
    private String name;

    private String description;

    @CreationTimestamp
    @Column(name="created_at", updatable = false)
    private LocalDateTime createdAt;

}
