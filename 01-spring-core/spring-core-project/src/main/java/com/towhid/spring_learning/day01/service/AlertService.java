package com.towhid.spring_learning.day01.service;

import com.towhid.spring_learning.day01.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AlertService {

    private final NotificationService primarySender;
    private final NotificationService backupSender;

    @Autowired
    public AlertService(
            NotificationService primarySender,        // @Primary = Email
            @Qualifier("smsNotificationService")
            NotificationService backupSender) {       // Specific = SMS

        this.primarySender = primarySender;
        this.backupSender = backupSender;

        System.out.println("AlertService created!");
    }

    public void sendAlert(String alert) {
        System.out.println("Sending Alert: " + alert);
        primarySender.send(alert);
        backupSender.send(alert);
    }
}
