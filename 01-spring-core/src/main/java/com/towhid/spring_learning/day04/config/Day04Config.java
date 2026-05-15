package com.towhid.spring_learning.day04.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
  DAY 4: Java Configuration
  Shows @Configuration and @Bean in depth
 */

@Configuration
public class Day04Config {

    // Basic Bean
    @Bean
    public String applicationName() {
        return "Spring Learning App";
    }

    // @Bean with initialization logic
    @Bean
    public DatabaseSimulator databaseSimulator() {
        DatabaseSimulator db = new DatabaseSimulator();
        db.setHost("localhost");
        db.setPort(3306);
        db.setMaxConnections(10);
        return db;
    }

    // @Bean that depends on another @Bean
    // Method parameter = Spring injects it automatically!
    @Bean
    public ConnectionPool connectionPool(
            DatabaseSimulator databaseSimulator) {
        return new ConnectionPool(databaseSimulator);
    }

    // @Bean with custom name
    @Bean(name = "myCustomCache")
    public java.util.Map<String, Object> cache() {
        return new java.util.HashMap<>();
    }

    // @Bean with init and destroy methods
    @Bean(initMethod = "initialize",
            destroyMethod = "shutdown")
    public ResourceManager resourceManager() {
        return new ResourceManager();
    }

}
