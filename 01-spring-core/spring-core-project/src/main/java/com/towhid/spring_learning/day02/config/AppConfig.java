package com.towhid.spring_learning.day02.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration          // ← This class contains bean definitions
public class AppConfig {

    @Bean
    public ArrayList<String> lang(){
        return new ArrayList<>(Arrays.asList("English","Spanish","French"));
    }

    @Bean               // ← This METHOD creates a bean
    public RestTemplate restTemplate() {
        System.out.println("🔨 Creating RestTemplate bean");
        return new RestTemplate();
    }

    @Bean
    public String appName() {
        return "Spring Learning App";
    }

    @Bean
    public Integer maxRetries() {
        return 3;
    }
}