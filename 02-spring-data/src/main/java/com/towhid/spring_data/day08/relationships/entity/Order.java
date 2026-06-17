package com.towhid.spring_data.day08.relationships.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
// 'order' is a reserved word in SQL!
// So we name the table 'orders' explicitly
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    // Enum stored as String in DB
    // e.g. "PENDING", "SHIPPED", "DELIVERED"
    @Enumerated(EnumType.STRING)
    // @Enumerated(EnumType.STRING): Instructs JPA to store the enum value
    // in the database as a text string (e.g., "PENDING")
    // rather than its numeric index.
    private OrderStatus status = OrderStatus.PENDING;

    @Column(name = "total_amount")
    private Double totalAmount = 0.0;

    @CreationTimestamp
    @Column(name = "order_date", updatable = false)
    private LocalDateTime orderDate;

    // MANY Orders → ONE Customer (owning side)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // ONE Order → MANY OrderItems
    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
            // EAGER = load items immediately with order
            // Makes sense here - you usually want
            // items when you fetch an order
    )
    private List<OrderItem> items = new ArrayList<>();

    public Order(String orderNumber, Customer customer) {
        this.orderNumber = orderNumber;
        this.customer = customer;
    }

    // Helper - add item and recalculate total
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
        // recalculate total
        this.totalAmount += item.getSubtotal();
    }

    // Enum for order status
    public enum OrderStatus {
        PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED
    }

    @Override
    public String toString() {
        return "Order{id=" + id +
                ", orderNumber=" + orderNumber +
                ", status=" + status +
                ", total=$" + totalAmount + "}";
    }
}