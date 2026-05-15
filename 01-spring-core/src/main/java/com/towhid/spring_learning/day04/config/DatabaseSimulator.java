package com.towhid.spring_learning.day04.config;

public class DatabaseSimulator {

    private String host;
    private int port;
    private int maxConnections;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getConnectionString() {
        return "jdbc:mysql://" + host + ":" + port;
    }

    public int getMaxConnections() {
        return maxConnections;
    }
}
