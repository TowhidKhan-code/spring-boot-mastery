package com.towhid.spring_learning.day01.service;

import com.towhid.spring_learning.day01.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    // ============================================================
    // TYPE 1: CONSTRUCTOR INJECTION ✅ (RECOMMENDED)
    // ============================================================
    private final NotificationService notificationService;

    @Autowired
    public PaymentService(@Qualifier("emailNotificationService")
                          NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    // WHY BEST?
    // - 'final' field = IMMUTABLE (cannot change after creation)
    // - Object always fully initialized
    // - Easy to unit test (just pass mock in constructor)
    // - Fails at startup if dependency missing
    // ============================================================


    // ============================================================
    // TYPE 2: SETTER INJECTION (Use for OPTIONAL dependencies)
    // ============================================================
    private NotificationService backupNotification;

    @Autowired(required = false)  // Won't crash if no bean found
    @Qualifier("sMSNotificationService")
    public void setBackupNotification(NotificationService backup) {
        this.backupNotification = backup;
    }
    // WHY USE?
    // - Optional dependencies (may or may not exist)
    // - Can change after object creation
    // ============================================================


    // ============================================================
    // TYPE 3: FIELD INJECTION ❌ (NOT RECOMMENDED)
    // ============================================================
    // @Autowired
    // private NotificationService fieldInjected;
    // WHY AVOID?
    // - Cannot be 'final'
    // - Hard to test (need reflection)
    // - Hides dependencies
    // - Only use in tests/quick demos
    // ============================================================


    public void processPayment(String orderId, double amount) {
        System.out.println("\nProcessing payment...");
        System.out.println("   Order: " + orderId);
        System.out.println("   Amount: $" + amount);

        // Primary notification
        notificationService.send("Payment of $" + amount + " received!");

        // Backup notification (if available)
        if (backupNotification != null) {
            backupNotification.send("Payment backup: $" + amount);
        }

        System.out.println("Payment processed!\n");
    }
}
