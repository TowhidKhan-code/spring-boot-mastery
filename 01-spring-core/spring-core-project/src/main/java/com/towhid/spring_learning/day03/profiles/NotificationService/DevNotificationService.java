package com.towhid.spring_learning.day03.profiles.NotificationService;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("dev")
public class DevNotificationService implements ProfileNotificationService{
    @Override
    public void sendNotification(String message) {
        System.out.println("[DEV] Console: " + message);
    }
}
