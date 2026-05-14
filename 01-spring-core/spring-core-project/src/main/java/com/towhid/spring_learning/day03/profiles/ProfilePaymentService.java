package com.towhid.spring_learning.day03.profiles;

/*
  DAY 3: Profile specific beans
  Different payment service for
  different environments
*/
public interface ProfilePaymentService {
    String processPayment(double amount);
}
