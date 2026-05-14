package com.towhid.spring_learning.day03.properties;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
  DAY 3: Spring Profiles
  Shows which environment is active
*/

@Component
public class EnvironmentInfo {
    // These come from active profile's properties
    @Value("${app.environment}")
    private String environment;

    @Value("${app.debug}")
    private boolean debug;

    @Value("${db.name}")
    private String dbName;

    @Value("${db.host}")
    private String dbHost;

    @Value("${app.name}")
    private String appName;

    @PostConstruct
    public void displayEnvironment() {
        System.out.println("\n" + "=".repeat(45));
        System.out.println("        ENVIRONMENT INFORMATION");
        System.out.println("=".repeat(45));
        System.out.println("  App Name    : " + appName);
        System.out.println("  Environment : " + environment);
        System.out.println("  Debug Mode  : " + debug);
        System.out.println("  Database    : " + dbName);
        System.out.println("  DB Host     : " + dbHost);
        System.out.println("=".repeat(45));
    }
}
