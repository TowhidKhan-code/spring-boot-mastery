package com.towhid.spring_learning.day01.notification;

import org.springframework.stereotype.Component;

@Component
public class SmsNotificationService implements NotificationService {
    @Override
    public void send(String message) {
        System.out.println("SMS SENT: " + message);
    }

    @Override
    public String getType() {
        return "SMS";
    }
}
