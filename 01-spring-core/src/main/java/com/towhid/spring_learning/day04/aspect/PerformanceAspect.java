package com.towhid.spring_learning.day04.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/*
  DAY 4: Performance Aspect
  Measures execution time of methods
  Uses @Around - most powerful advice type!

  Also handles @TrackTime custom annotation
 */
@Aspect
@Component
public class PerformanceAspect {

    // ================================================
    // @Around with bankService methods
    // ================================================
    @Around("execution(* com.towhid.spring_learning"
            + ".day04.service.*.*(..))")
    public Object measureTime(ProceedingJoinPoint joinPoint)
            throws Throwable {

        // BEFORE - record start time
        long startTime = System.currentTimeMillis();
        String methodName = joinPoint.getSignature().getName();

        System.out.println("[PERF] Starting: " + methodName);

        Object result;
        try {
            // Execute the ACTUAL method
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            long errorTime = System.currentTimeMillis() - startTime;
            System.out.println("[PERF] Failed after: "
                    + errorTime + "ms");
            throw throwable;
        }

        // AFTER - calculate time
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[PERF] Completed in: "
                + duration + "ms");

        return result;
    }

    // ================================================
    // @Around with custom @TrackTime annotation
    // ================================================
    @Around("@annotation(com.towhid.spring_learning"
            + ".day04.aspect.TrackTime)")
    public Object trackTime(ProceedingJoinPoint joinPoint)
            throws Throwable {

        long start = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        System.out.println("🎯 [@TrackTime] "
                + joinPoint.getSignature().getName()
                + " took " + (end - start) + "ms");

        return result;
    }
}