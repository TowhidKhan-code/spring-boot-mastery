package com.towhid.spring_mvc.day10.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// What client sends when
// CREATING or UPDATING a product
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private String category;
    private String imageUrl;
    // active defaults to true in entity
    // client doesn't need to send it
}