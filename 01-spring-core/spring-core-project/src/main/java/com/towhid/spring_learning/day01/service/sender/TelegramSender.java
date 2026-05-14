package com.towhid.spring_learning.day01.service.sender;

import org.springframework.stereotype.Component;

@Component
public class TelegramSender implements MessageSender{
    @Override
    public void sendMessage(String to, String content) {
        System.out.println("Telegram to " + to + ": " + content);
    }

    @Override
    public String getSenderName() {
        return "Telegram";
    }
}
