package com.towhid.spring_learning.day03.profiles.NotificationService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("test")
public class TestNotificationService implements ProfileNotificationService{
    @Override
    public void sendNotification(String message) {
        System.out.println("[TEST] Mock notified: " + message);
    }
}
