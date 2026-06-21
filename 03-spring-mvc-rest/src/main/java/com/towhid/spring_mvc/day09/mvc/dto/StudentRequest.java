package com.towhid.spring_mvc.day09.mvc.dto;

import jakarta.validation.constraints.*;
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
    @NotBlank(message = "Name cannot be empty")
    @Size(min=2,max = 100,message = "Name needs to be between 2 to 100 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Enter a valid email")
    private String email;

    @NotNull(message = "Age is required")
    @Min(value = 16,message = "Age must be between 16 to 100")
    @Max(value = 100,message = "Age must be between 16 to 100")
    private Integer age;

    @NotBlank(message = "Course cannot be empty")
    private String course;

    @NotNull(message = "Grade is required")
    @DecimalMin(value = "0.0",message = "Grade must be between 0.0 to 10.0")
    @DecimalMax(value = "10.0",message = "Grade must be between 0.0 to 10.0")
    private Double grade;
    // no id → DB generates it
    // no createdAt/updatedAt → auto generated
}