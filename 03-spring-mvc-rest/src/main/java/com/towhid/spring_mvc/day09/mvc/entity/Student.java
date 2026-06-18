package com.towhid.spring_mvc.day09.mvc.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "mvc_students")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // name of student
    @Column(nullable = false)
    private String name;

    // email - must be unique
    @Column(unique = true, nullable = false)
    private String email;

    // age of student
    private Integer age;

    // which course enrolled in
    private String course;

    // grade out of 10
    private Double grade;

    // auto set when created
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;


    // auto set when updated
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
