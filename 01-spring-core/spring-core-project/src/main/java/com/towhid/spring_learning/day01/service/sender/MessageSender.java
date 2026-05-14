package com.towhid.spring_learning.day01.service.sender;

public interface MessageSender {
    void sendMessage(String to,String content);

    default String getSenderName(){
        return "UNKNOWN";
    }
}
