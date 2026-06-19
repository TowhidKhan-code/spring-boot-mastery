package com.towhid.spring_mvc.day10.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// What WE send back to client
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    private Integer id;
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    private Boolean active;
    private LocalDateTime createdAt;
    // not including updatedAt - our choice
}