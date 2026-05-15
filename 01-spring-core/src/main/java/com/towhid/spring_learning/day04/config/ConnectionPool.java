package com.towhid.spring_learning.day04.config;

public class ConnectionPool {

    private final DatabaseSimulator database;
    private boolean active;

    public ConnectionPool(DatabaseSimulator database) {
        this.database = database;
        this.active = true;
        System.out.println(" ConnectionPool created!");
        System.out.println("   Connected to: "
                + database.getConnectionString());
        System.out.println("   Max connections: "
                + database.getMaxConnections());
    }

    public boolean isActive() {
        return active;
    }
}
