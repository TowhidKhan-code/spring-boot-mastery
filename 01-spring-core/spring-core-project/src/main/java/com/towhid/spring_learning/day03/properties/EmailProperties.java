package com.towhid.spring_learning.day03.properties;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "email")
public class EmailProperties {
    private String host;
    private int port;
    private String username;
    private String password;
    private String from;
    private int maxEmailsPerDay;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public int getMaxEmailsPerDay() {
        return maxEmailsPerDay;
    }

    public void setMaxEmailsPerDay(int maxEmailsPerDay) {
        this.maxEmailsPerDay = maxEmailsPerDay;
    }

    @PostConstruct
    public void displayProperties(){
        System.out.println("Email Config Ready!");
        System.out.println("SMTP: " + getHost());
        System.out.println("From: " + getFrom());
        System.out.println("Daily Limit: " + getMaxEmailsPerDay());
    }
}
