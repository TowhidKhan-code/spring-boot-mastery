package com.towhid.spring_learning.day01.notification;

// This is the CONTRACT
// Any notification type MUST implement this
public interface NotificationService {

    void send(String message);

    default String getType(){
        return "UNKNOWN";
    }

}
