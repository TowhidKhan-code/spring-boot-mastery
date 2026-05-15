package com.towhid.spring_learning.day04.autowiring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/*
  Circular Dependency Problem and Solution

  PROBLEM:
  ServiceA needs ServiceB
  ServiceB needs ServiceA
  Spring gets confused! Which to create first?

  SOLUTION: @Lazy on one of them
 */
@Service
public class ServiceA {

    private final ServiceB serviceB;

    @Autowired
    public ServiceA(@Lazy ServiceB serviceB) {
        // @Lazy = Don't create ServiceB now
        // Create it when first used!
        this.serviceB = serviceB;
        System.out.println("✅ ServiceA created!");
    }

    public String doSomething() {
        return "ServiceA -> " + serviceB.help();
    }
}
