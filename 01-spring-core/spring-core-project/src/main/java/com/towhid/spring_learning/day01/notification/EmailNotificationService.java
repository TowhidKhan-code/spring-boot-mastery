package com.towhid.spring_learning.day01.notification;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component // 👈 THIS IS THE KEY!
           // Tells Spring: "Hey! I'm a bean. Create me and manage me!"
@Primary
public class EmailNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("EMAIL SENT: " + message);
    }

    @Override
    public String getType() {
        return "EMAIL";
    }
}
