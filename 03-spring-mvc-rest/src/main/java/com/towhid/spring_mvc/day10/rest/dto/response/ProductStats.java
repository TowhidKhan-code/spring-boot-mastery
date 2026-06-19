package com.towhid.spring_mvc.day10.rest.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductStats {

    private long totalProducts;
    private long activeProducts;
    private long totalCategories;
    private long lowStockCount;

}
