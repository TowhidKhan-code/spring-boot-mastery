package com.towhid.spring_mvc.day09.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseResponse {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
}
