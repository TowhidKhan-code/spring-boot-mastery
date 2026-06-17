package com.towhid.spring_data.day08.relationships.repository;

import com.towhid.spring_data.day08.relationships.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository
        extends JpaRepository<Order, Integer> {

    // Find all orders for a customer
    List<Order> findByCustomerId(Integer customerId);

    // Find by status
    List<Order> findByStatus(Order.OrderStatus status);

    // Find orders above a certain amount
    List<Order> findByTotalAmountGreaterThan(Double amount);

    // JPQL - orders with their items loaded
    @Query("SELECT DISTINCT o FROM Order o " +
            "LEFT JOIN FETCH o.items " +
            "WHERE o.customer.id = :customerId")
    List<Order> findOrdersWithItems(
            @Param("customerId") Integer customerId);
}