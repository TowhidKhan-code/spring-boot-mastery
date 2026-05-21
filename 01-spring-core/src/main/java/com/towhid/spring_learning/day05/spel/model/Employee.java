package com.towhid.spring_learning.day05.spel.model;


import org.springframework.stereotype.Component;

// This is a simple Spring bean (model)
// SpEL will reference properties from this bean
@Component
public class Employee {

    // These are plain fields — no @Value here
    // SpEL from OTHER classes will read these
    private String name = "Towhid";
    private double salary = 50000.0;
    private String department = "It";
    private int age = 25;
    private boolean active = true;

    // Getters — SpEL needs these to access properties
    public String getName() { return name; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public int getAge() { return age; }
    public boolean isActive() { return active; }

    // A method SpEL can call
    public double getAnnualBonus() {
        return salary * 0.10; // 10% bonus
    }
}
