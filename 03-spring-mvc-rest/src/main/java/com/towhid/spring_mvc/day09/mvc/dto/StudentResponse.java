package com.towhid.spring_mvc.day09.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// What WE send back to client
// We control exactly what they see
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponse {

    private Integer id;
    private String name;
    private String email;
    private Integer age;
    private String course;
    private Double grade;
    private LocalDateTime createdAt;
    // we choose to include createdAt
    // but NOT updatedAt (our choice!)
}