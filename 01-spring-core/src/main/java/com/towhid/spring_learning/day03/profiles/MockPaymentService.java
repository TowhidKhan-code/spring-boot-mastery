package com.towhid.spring_learning.day03.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
  Only active in TEST profile
  Mock payment for testing
*/
@Service
@Profile("test")            // ← Only created in TEST!
public class MockPaymentService implements ProfilePaymentService {

    @Override
    public String processPayment(double amount) {
        return "🧪 [TEST] Mock payment of $"
                + amount + " processed for testing!";
    }
}