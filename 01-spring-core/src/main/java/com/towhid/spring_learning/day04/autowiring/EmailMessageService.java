package com.towhid.spring_learning.day04.autowiring;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary     // Default when multiple found
public class EmailMessageService implements MessageService{
    @Override
    public String getMessage() {
        return "Hello via Email!";
    }

    @Override
    public String getServiceName() {
        return "EmailMessageService";
    }
}
