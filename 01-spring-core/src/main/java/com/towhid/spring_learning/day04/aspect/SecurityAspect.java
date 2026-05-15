package com.towhid.spring_learning.day04.aspect;

import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SecurityAspect {


    @Pointcut("execution(* com.towhid.spring_learning.day04.service.*.*(..))")
    public void bankServiceMethods() {}


    @Before("bankServiceMethods")
    public void checkBefore(){
        System.out.println("Security check passed!");
    }

    @Around("@annotation(com.towhid.spring_learning"
            + ".day04.aspect.RequiresAdmin)")
    public void checkAfter(){
        System.out.println("Admin Check...");
        double random = Math.random();
        if(random>0.3){
            System.out.println("Access Granted");
        }else{
            throw new SecurityException("Access Denied");
        }
    }
}
