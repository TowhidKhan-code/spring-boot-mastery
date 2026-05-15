package com.towhid.spring_learning.day04.service;

import com.towhid.spring_learning.day04.aspect.RequiresAdmin;
import com.towhid.spring_learning.day04.aspect.TrackTime;
import org.springframework.stereotype.Service;

/*
 DAY 4: AOP Target
 This service has NO logging code
 AOP adds logging automatically!
 */

@Service
public class BankService {

    @TrackTime
    public String deposit(String account,double amount){
        System.out.println("Processing deposit...");
        return "Deposited $ " + amount + " to account: " + account;
    }

    public String withdraw(String account, double amount) {
        System.out.println("Processing Withdrawal...");

        // Simulate insufficient funds
        if(amount>10000){
            throw new RuntimeException("Insufficient funds for: $" + amount);
        }

        return "Withdrew $" + amount
                + " from account: " + account;
    }

    public double getBalance(String account){
        System.out.println("Checking balance...");
        return 5000.00;
    }

    @RequiresAdmin
    public String transfer(String from,
                           String to,
                           double amount) {
        System.out.println("   🔄 Processing transfer...");
        return "Transferred $" + amount
                + " from " + from + " to " + to;
    }
}
