package com.towhid.spring_learning.day01.service.sender;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SlackSender implements MessageSender{
    @Override
    public void sendMessage(String to, String content) {
        System.out.println("Slack to " + to + ": " + content);
    }

    @Override
    public String getSenderName() {
        return "Slack";
    }
}
