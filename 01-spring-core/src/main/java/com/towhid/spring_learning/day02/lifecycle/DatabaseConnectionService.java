package com.towhid.spring_learning.day02.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConnectionService {

    private String connectionStatus;

    // Constructor runs FIRST
    public DatabaseConnectionService() {
        System.out.println("\n🔧 STEP 1: Constructor called");
        System.out.println("   Dependencies NOT yet injected here");
        this.connectionStatus = "NOT CONNECTED";
    }

    // Runs AFTER constructor and dependency injection
    @PostConstruct
    public void initialize() {
        System.out.println("🔧 STEP 2: @PostConstruct called");
        System.out.println("   All dependencies are injected now");
        System.out.println("   Opening database connection...");
        this.connectionStatus = "CONNECTED";
        System.out.println("   Status: " + connectionStatus);
    }

    // Runs BEFORE Spring destroys the bean
    @PreDestroy
    public void cleanup() {
        System.out.println("🔧 STEP 3: @PreDestroy called");
        System.out.println("   Closing database connection...");
        this.connectionStatus = "DISCONNECTED";
        System.out.println("   Status: " + connectionStatus);
    }

    public String getStatus() {
        return connectionStatus;
    }
}
