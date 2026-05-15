package com.towhid.spring_learning.day04.config;

public class ResourceManager {

    private boolean initialized;

    // Called by @Bean(initMethod = "initialize")
    public void initialize() {
        this.initialized = true;
        System.out.println("ResourceManager initialized!");
    }

    // Called by @Bean(destroyMethod = "shutdown")
    public void shutdown() {
        this.initialized = false;
        System.out.println("ResourceManager shutdown!");
    }

    public boolean isInitialized() {
        return initialized;
    }
}
