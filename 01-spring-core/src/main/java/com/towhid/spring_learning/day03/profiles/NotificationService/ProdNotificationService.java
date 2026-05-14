package com.towhid.spring_learning.day03.profiles.NotificationService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("prod")
public class ProdNotificationService implements ProfileNotificationService{
    @Override
    public void sendNotification(String message) {
        System.out.println("[PROD] Email sent: " + message);
    }
}
