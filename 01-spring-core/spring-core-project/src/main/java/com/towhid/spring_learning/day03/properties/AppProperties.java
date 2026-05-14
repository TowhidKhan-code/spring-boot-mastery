package com.towhid.spring_learning.day03.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*
 * DAY 3: @ConfigurationProperties Approach
 * Demonstrates reading ALL related properties
 * at once using prefix binding
 *
 * Compare with Day 2: AppValueProperties.java
 * which reads ONE property at a time using @Value
 */


@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {


    // Field names must match property names
    // app.name     → name
    // app.version  → version
    // app.author   → author
    // app.max-retries → maxRetries (auto converted)
    // app.timeout  → timeout
    // app.debug    → debug

    private String name;
    private String version;
    private String author;
    private int maxRetries;
    private long timeout;
    private boolean debug;

    // ⚠️ GETTERS AND SETTERS ARE MANDATORY!
    // Spring uses setters to bind properties
    // Without setters = properties stay null!

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getVersion() { return version; }
    public void setVersion(String version) { this.version = version; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public int getMaxRetries() { return maxRetries; }
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public long getTimeout() { return timeout; }
    public void setTimeout(long timeout) { this.timeout = timeout; }

    public boolean isDebug() { return debug; }
    public void setDebug(boolean debug) { this.debug = debug; }

    @PostConstruct
    public void display() {
        System.out.println("\n=== APP PROPERTIES ===");
        System.out.println("Name:        " + name);
        System.out.println("Version:     " + version);
        System.out.println("Author:      " + author);
        System.out.println("MaxRetries:  " + maxRetries);
        System.out.println("Timeout:     " + timeout + "ms");
        System.out.println("Debug Mode:  " + debug);
    }

}