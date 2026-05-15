package com.towhid.spring_learning.day04.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutowiringDemo {

    // 1. Injects @Primary = EmailMessageService
    private final MessageService primaryService;

    // 2. Injects specific = SMSMessageService
    private final MessageService smsService;

    // 3. Injects by NAME = pushMessageService
    private final MessageService pushMessageService;

    // 4. Injects ALL implementations
    private final List<MessageService> allServices;

    @Autowired
    public AutowiringDemo(
            // No qualifier = uses @Primary
            MessageService primaryService,

            // @Qualifier = specific bean
            @Qualifier("sMSMessageService")
            MessageService smsService,

            // Variable name matches bean name
            MessageService pushMessageService,

            // Injects ALL MessageService beans
            List<MessageService> allServices) {

        this.primaryService = primaryService;
        this.smsService = smsService;
        this.pushMessageService = pushMessageService;
        this.allServices = allServices;
    }

    public void demonstrateAutowiring() {
        System.out.println("\n=== AUTOWIRING DEMO ===");

        System.out.println("\n1. @Primary injection:");
        System.out.println("   → " + primaryService.getServiceName());
        System.out.println("   → " + primaryService.getMessage());

        System.out.println("\n2. @Qualifier injection:");
        System.out.println("   → " + smsService.getServiceName());
        System.out.println("   → " + smsService.getMessage());

        System.out.println("\n3. By variable name:");
        System.out.println("   → " + pushMessageService.getServiceName());
        System.out.println("   → " + pushMessageService.getMessage());

        System.out.println("\n4. ALL implementations:");
        allServices.forEach(service ->
                System.out.println("   → " + service.getServiceName()));
    }
}
