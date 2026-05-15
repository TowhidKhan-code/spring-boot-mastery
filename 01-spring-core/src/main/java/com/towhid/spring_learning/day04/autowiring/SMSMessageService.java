package com.towhid.spring_learning.day04.autowiring;

import org.springframework.stereotype.Service;

@Service("sMSMessageService")
public class SMSMessageService implements MessageService{
    @Override
    public String getMessage() {
        return "Hello via SMS!";
    }

    @Override
    public String getServiceName() {
        return "SMSMessageService";
    }
}
