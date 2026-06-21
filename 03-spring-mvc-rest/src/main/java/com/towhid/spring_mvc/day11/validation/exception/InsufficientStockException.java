//---------------------------------------
// Practice Task 2 :-
// Create custom exception:
// InsufficientStockException
//      thrown when requested quantity > available stock
//      return 400 Bad Request
//      message like:
//      "Insufficient stock. Available: X, Requested: Y"
//---------------------------------------

package com.towhid.spring_mvc.day11.validation.exception;

public class InsufficientStockException
        extends RuntimeException{
    private final Integer stockAmount;
    private final Integer requestedAmount;

    public InsufficientStockException(
            Integer stockAmount,Integer requestedAmount){
        super(String.format(
                "Insufficient stock. Available: %d, Requested: %d",
                stockAmount,
                requestedAmount));
        this.stockAmount = stockAmount;
        this.requestedAmount = requestedAmount;
    }

    public Integer getStockAmount() {
        return stockAmount;
    }

    public Integer getRequestedAmount() {
        return requestedAmount;
    }
}
