package com.towhid.spring_learning.day02.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConfig {

    @Value("${db.host}")
    private String host;

    @Value("${db.port}")
    private int port;

    @Value("${db.name}")
    private String name;

    @Value("${db.max-connections}")
    private int maxConnections;

    @PostConstruct
    public void display(){
        System.out.println("Connecting to: " + host + ":" + port + "/" + name);
        System.out.println("Max Connections: " + maxConnections);
    }
}
