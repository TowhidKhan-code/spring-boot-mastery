package com.towhid.spring_learning.day03.profiles;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/*
 Only active in PROD profile
 Real payment processing
*/
@Service
@Profile("prod")            // ← Only created in PROD!
public class RealPaymentService implements ProfilePaymentService {

    @Override
    public String processPayment(double amount) {
        return "[PROD] Real payment of $"
                + amount + " processed via Stripe!";
    }
}