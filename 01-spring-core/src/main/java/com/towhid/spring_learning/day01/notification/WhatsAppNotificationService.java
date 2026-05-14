package com.towhid.spring_learning.day01.notification;

import org.springframework.stereotype.Component;

@Component
//@Primary  // 👈 This will be the DEFAULT choice
public class WhatsAppNotificationService implements NotificationService {

    @Override
    public void send(String message) {
        System.out.println("💬 WHATSAPP SENT: " + message);
    }

    @Override
    public String getType() {
        return "WHATSAPP";
    }
}
