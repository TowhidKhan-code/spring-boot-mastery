package com.towhid.spring_learning.day03.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
  Only active in DEV profile
  Fake payment - always succeeds
*/
@Service
@Profile("dev")             // ← Only created in DEV!
public class FakePaymentService implements ProfilePaymentService {
    @Override
    public String processPayment(double amount) {
        return "[DEV] Fake payment of $"
                + amount + " processed! (Not real)";
    }
}
