package com.towhid.spring_mvc.day09.mvc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// What WE send back to client
// We control exactly what they see
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Student response object")
public class StudentResponse {

    @Schema(description = "Student ID", example = "1")
    private Integer id;

    @Schema(description = "Full name", example = "Towhid Khan")
    private String name;

    @Schema(description = "Email address", example = "towhid@email.com")
    private String email;

    @Schema(description = "Age", example = "25")
    private Integer age;

    @Schema(description = "Course enrolled in", example = "Spring Boot")
    private String course;

    @Schema(description = "Grade", example = "9.5")
    private Double grade;

    @Schema(description = "Created timestamp")
    private LocalDateTime createdAt;
    // we choose to include createdAt
    // but NOT updatedAt (our choice!)
}