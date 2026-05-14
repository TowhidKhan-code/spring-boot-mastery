package com.towhid.spring_learning.day03.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "db")
public class DatabaseProperties {

    private String host;
    private int port;
    private String name;
    private String password;
    private int maxConnections;

    // Getters and Setters
    public String getHost() { return host; }
    public void setHost(String host) { this.host = host; }

    public int getPort() { return port; }
    public void setPort(int port) { this.port = port; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPassword() { return password; }
    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxConnections() { return maxConnections; }
    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    @PostConstruct
    public void display() {
        System.out.println("\n=== DATABASE PROPERTIES ===");
        System.out.println("Host:            " + host);
        System.out.println("Port:            " + port);
        System.out.println("Database:        " + name);
        System.out.println("Password:        " + password);
        System.out.println("Max Connections: " + maxConnections);
        System.out.println("Full URL:        jdbc:mysql://"
                + host + ":" + port + "/" + name);
    }
}