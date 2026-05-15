package com.towhid.spring_learning.day04.autowiring;

import org.springframework.stereotype.Service;

@Service
public class PushMessageService implements MessageService{
    @Override
    public String getMessage() {
        return "Hello via Push!";
    }

    @Override
    public String getServiceName() {
        return "PushMessageService";
    }
}
