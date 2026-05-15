package com.towhid.spring_learning.day04.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import java.util.Arrays;

/**
 * DAY 4: Logging Aspect
 * Automatically logs ALL methods in day04.service
 * WITHOUT touching any service code!
 */
@Aspect           // This class is an Aspect
@Component        // Spring manages it as a bean
public class LoggingAspect {

    // ================================================
    // POINTCUT - Defines WHERE to apply advice
    // ================================================

    // Matches ALL methods in day04.service package
    @Pointcut("execution(* com.towhid.spring_learning.day04.service.*.*(..))")
    public void bankServiceMethods() {}

    // ================================================
    // @Before - Runs BEFORE the method
    // ================================================
    @Before("bankServiceMethods()")
    public void logBefore(JoinPoint joinPoint) {
        System.out.println("\n[LOG] Method called: "
                + joinPoint.getSignature().getName());
        System.out.println("[LOG] Arguments: "
                + Arrays.toString(joinPoint.getArgs()));
        System.out.println("[LOG] Class: "
                + joinPoint.getTarget().getClass().getSimpleName());
    }

    // ================================================
    // @After - Runs AFTER method (success or failure)
    // ================================================
    @After("bankServiceMethods()")
    public void logAfter(JoinPoint joinPoint) {
        System.out.println("[LOG] Method completed: "
                + joinPoint.getSignature().getName());
    }

    // ================================================
    // @AfterReturning - Runs after SUCCESS
    // ================================================
    @AfterReturning(
            pointcut = "bankServiceMethods()",
            returning = "result"
    )
    public void logAfterSuccess(JoinPoint joinPoint,
                                Object result) {
        System.out.println("[LOG] Success - Returned: "
                + result);
    }

    // ================================================
    // @AfterThrowing - Runs after EXCEPTION
    // ================================================
    @AfterThrowing(
            pointcut = "bankServiceMethods()",
            throwing = "exception"
    )
    public void logAfterException(JoinPoint joinPoint,
                                  Exception exception) {
        System.out.println("[LOG] Exception in: "
                + joinPoint.getSignature().getName());
        System.out.println("[LOG] Reason: "
                + exception.getMessage());
    }
}