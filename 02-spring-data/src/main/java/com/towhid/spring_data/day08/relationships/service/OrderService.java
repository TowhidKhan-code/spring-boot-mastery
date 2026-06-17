package com.towhid.spring_data.day08.relationships.service;

import com.towhid.spring_data.day08.relationships.entity.*;
import com.towhid.spring_data.day08.relationships.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    public OrderService(
            CustomerRepository customerRepository,
            OrderRepository orderRepository) {
        this.customerRepository = customerRepository;
        this.orderRepository = orderRepository;
    }

    // ─────────────────────────────────────────
    // CREATE CUSTOMER
    // ─────────────────────────────────────────
    public Customer createCustomer(
            String name, String email, String phone) {

        if (customerRepository.existsByEmail(email)) {
            throw new RuntimeException(
                    "Email already exists: " + email);
        }

        Customer customer = new Customer(name, email, phone);
        Customer saved = customerRepository.save(customer);
        System.out.println("Customer created: " + name);
        return saved;
    }

    // ─────────────────────────────────────────
    // PLACE ORDER
    // This is a multi-step transaction!
    // ALL steps succeed or ALL rollback
    // ─────────────────────────────────────────
    public Order placeOrder(
            Integer customerId,
            List<OrderItem> items) {

        // Step 1: Verify customer exists
        Customer customer = customerRepository
                .findById(customerId)
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found: " + customerId));

        // Step 2: Generate unique order number
        String orderNumber = "ORD-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();

        // Step 3: Create order
        Order order = new Order(orderNumber, customer);

        // Step 4: Add items to order
        for (OrderItem item : items) {
            order.addItem(item);
            // addItem() also updates totalAmount!
        }

        // Step 5: Link order to customer
        customer.addOrder(order);

        // Step 6: Save — cascades save items too!
        Order saved = orderRepository.save(order);

        System.out.println("Order placed: "
                + orderNumber
                + " | Total: $" + saved.getTotalAmount());

        return saved;
    }

    // ─────────────────────────────────────────
    // UPDATE ORDER STATUS
    // ─────────────────────────────────────────
    public Order updateOrderStatus(
            Integer orderId,
            Order.OrderStatus newStatus) {

        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Order not found: " + orderId));

        Order.OrderStatus oldStatus = order.getStatus();
        order.setStatus(newStatus);

        Order updated = orderRepository.save(order);

        System.out.println("Order " + order.getOrderNumber()
                + ": " + oldStatus + " → " + newStatus);

        return updated;
    }

    // ─────────────────────────────────────────
    // CANCEL ORDER
    // ─────────────────────────────────────────
    public void cancelOrder(Integer orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new RuntimeException(
                        "Order not found: " + orderId));

        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new RuntimeException(
                    "Cannot cancel delivered order!");
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
        System.out.println("Order cancelled: "
                + order.getOrderNumber());
    }

    // ─────────────────────────────────────────
    // GET CUSTOMER WITH ORDERS
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public Customer getCustomerWithOrders(Integer id) {
        return customerRepository
                .findByIdWithOrders(id)
                .orElseThrow(() -> new RuntimeException(
                        "Customer not found: " + id));
    }

    // ─────────────────────────────────────────
    // GET ALL ORDERS BY STATUS
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(
            Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
}