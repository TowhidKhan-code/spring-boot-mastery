package com.towhid.spring_learning.day01.service.sender;

import org.springframework.stereotype.Component;

@Component
public class GmailSender implements MessageSender{
    @Override
    public void sendMessage(String to, String content) {
        System.out.println("Gmail to " + to + ": " + content);
    }

    @Override
    public String getSenderName() {
        return "Gmail";
    }
}
