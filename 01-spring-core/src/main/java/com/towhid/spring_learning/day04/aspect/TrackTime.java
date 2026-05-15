package com.towhid.spring_learning.day04.aspect;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
  DAY 4: Custom AOP Annotation
  Add this to any method to track its time!

  Usage:
  @TrackTime
  public void someMethod() { }
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TrackTime {
    // Marker annotation - no fields needed
}
