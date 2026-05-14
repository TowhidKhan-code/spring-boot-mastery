package com.towhid.spring_learning.day02.scope;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype") // ← NEW object every time
public class ShoppingCart {

    private String cartId;

    public ShoppingCart() {
        // Random ID to prove different objects
        this.cartId = "CART-" + (int)(Math.random() * 10000);
        System.out.println("New ShoppingCart created: " + cartId);
    }

    public void addItem(String item) {
        System.out.println("Added " + item + " to " + cartId);
    }

    public String getCartId() {
        return cartId;
    }
}
