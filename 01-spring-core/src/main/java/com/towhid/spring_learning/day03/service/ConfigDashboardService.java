package com.towhid.spring_learning.day03.service;

import com.towhid.spring_learning.day03.properties.AppProperties;
import com.towhid.spring_learning.day03.properties.DatabaseProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigDashboardService {
    private final AppProperties appProperties;
    private final DatabaseProperties dbProperties;

    @Autowired
    public ConfigDashboardService(AppProperties appProperties,DatabaseProperties dbProperties){
        this.appProperties = appProperties;
        this.dbProperties = dbProperties;
    }

    public void printDashboard() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("      APPLICATION DASHBOARD");
        System.out.println("=".repeat(40));
        System.out.println("App Name:    " + appProperties.getName());
        System.out.println("Version:     " + appProperties.getVersion());
        System.out.println("Author:      " + appProperties.getAuthor());
        System.out.println("Debug:       " + appProperties.isDebug());
        System.out.println("─".repeat(40));
        System.out.println("DB Host:     " + dbProperties.getHost());
        System.out.println("DB Port:     " + dbProperties.getPort());
        System.out.println("DB Name:     " + dbProperties.getName());
        System.out.println("Max Conn:    " + dbProperties.getMaxConnections());
        System.out.println("=".repeat(40));
    }
}
