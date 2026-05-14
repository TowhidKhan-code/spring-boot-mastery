package com.towhid.spring_learning.day01.service;

import com.towhid.spring_learning.day01.service.sender.MessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    private final MessageSender primarySender;
    private final MessageSender backupSender;

    @Autowired
    public NotificationManager(
            MessageSender primarySender,  // Gets @Primary = Slack
            @Qualifier("gmailSender") MessageSender backupSender) {
        this.primarySender = primarySender;
        this.backupSender = backupSender;
    }

    public void notifyUser(String username, String message) {
        System.out.println("\n🔔 Sending notification to: " + username);

        primarySender.sendMessage(username, message);
        backupSender.sendMessage(username, message);

        System.out.println("✅ Notification sent to " + username +
                " via " + primarySender.getSenderName() +
                " and " + backupSender.getSenderName());
    }
}
