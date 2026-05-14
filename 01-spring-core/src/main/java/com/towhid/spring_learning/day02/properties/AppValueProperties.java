package com.towhid.spring_learning.day02.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/*
 * DAY 2: @Value Approach
 * Demonstrates reading properties ONE AT A TIME
 * using @Value annotation
 *
 * Compare with Day 3: AppProperties.java
 * which uses @ConfigurationProperties
 * to read ALL properties at once!
 */

@Component
public class AppValueProperties {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String version;

    @Value("${app.author}")
    private String author;

    @Value("${app.max-retries}")
    private int maxRetries;

    @Value("${app.timeout}")
    private long timeout;

    @Value("${app.debug}")
    private boolean debug;

    @Value("${app.welcome-message}")
    private String welcomeMessage;

    // Default value if property not found
    @Value("${app.missing-property:Default Value}")
    private String missingProperty;

    @PostConstruct
    public void displayProperties() {
        System.out.println("\n=== APPLICATION PROPERTIES ===");
        System.out.println("Name:            " + appName);
        System.out.println("Version:         " + version);
        System.out.println("Author:          " + author);
        System.out.println("Max Retries:     " + maxRetries);
        System.out.println("Timeout:         " + timeout + "ms");
        System.out.println("Debug:           " + debug);
        System.out.println("Welcome:         " + welcomeMessage);
        System.out.println("Missing Prop:    " + missingProperty);
    }
}
