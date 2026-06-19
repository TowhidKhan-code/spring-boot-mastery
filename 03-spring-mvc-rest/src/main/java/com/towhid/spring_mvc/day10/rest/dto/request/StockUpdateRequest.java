package com.towhid.spring_mvc.day10.rest.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Used for PATCH /api/products/{id}/stock
// Client sends ONLY the stock quantity
// Not the whole product!
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateRequest {

    private Integer stockQuantity;
    // only field we need for stock update
}