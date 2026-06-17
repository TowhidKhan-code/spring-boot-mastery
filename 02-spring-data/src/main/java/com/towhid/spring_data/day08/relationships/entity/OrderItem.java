package com.towhid.spring_data.day08.relationships.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_name", nullable = false)
    private String productName;

    private Integer quantity;

    @Column(name = "unit_price")
    private Double unitPrice;

    // Calculated field: quantity * unitPrice
    // @Transient = NOT stored in DB
    // Hibernate ignores this field
    @Transient
    private Double subtotal;

    // MANY OrderItems → ONE Order
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(String productName,
                     Integer quantity,
                     Double unitPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    // Calculate subtotal
    public Double getSubtotal() {
        if (quantity != null && unitPrice != null) {
            return quantity * unitPrice;
        }
        return 0.0;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "product=" + productName +
                ", qty=" + quantity +
                ", price=$" + unitPrice +
                ", subtotal=$" + getSubtotal() + "}";
    }
}