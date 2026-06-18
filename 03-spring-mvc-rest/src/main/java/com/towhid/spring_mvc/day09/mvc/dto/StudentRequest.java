package com.towhid.spring_mvc.day09.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// What the CLIENT sends to us
// POST /api/students → body is this DTO
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    // client sends these fields
    private String name;
    private String email;
    private Integer age;
    private String course;
    private Double grade;
    // no id → DB generates it
    // no createdAt/updatedAt → auto generated
}