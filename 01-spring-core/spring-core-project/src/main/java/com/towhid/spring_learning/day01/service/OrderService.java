package com.towhid.spring_learning.day01.service;

import com.towhid.spring_learning.day01.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    // Spring will INJECT this dependency
    private final NotificationService notificationService;

    // Constructor Injection
    @Autowired // 👈 Tell Spring to inject NotificationService here
    public OrderService(
            @Qualifier("smsNotificationService")
                        NotificationService notificationService){
        this.notificationService = notificationService;
        System.out.println("OrderService created!");
        System.out.println("Using: " + notificationService.getType());
    }

    public void placeOrder(String item,double price){
        System.out.println("\n🛒 Processing order...");
        System.out.println("   Item: " + item);
        System.out.println("   Price: $" + price);

        // Business Logic
        String confirmationMessage = String.format("Order Confirmed! Item: %s, Price: %.2f", item,price);

        // Notify customer
        notificationService.send(confirmationMessage);
        System.out.println("Order completed!\n");
    }
}
