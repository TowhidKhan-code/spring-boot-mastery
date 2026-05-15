package com.towhid.spring_learning.day04.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceB {

    private final ServiceA serviceA;

    @Autowired
    public ServiceB(ServiceA serviceA){
        this.serviceA = serviceA;
        System.out.println("✅ ServiceB created!");
    }

    public String help() {
        return "ServiceB helped!";
    }
}
