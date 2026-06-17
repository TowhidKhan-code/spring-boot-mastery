package com.towhid.spring_data.day08.relationships.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy  = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String name;

    private String location;

    // ONE Department has MANY Employees
    // mappedBy = "department" means:
    // Employee class has a field called 'department'
    // that owns this relationship
    @OneToMany(
            // The Value of mappedBy: Must match the exact variable name
            // of the parent field inside the child class.
            mappedBy = "department",
            // CascadeType.ALL = any operation on Department
            // (save, delete) cascades to its Employees
            cascade = CascadeType.ALL,
            // orphanRemoval = if Employee removed from list
            // delete it from DB automatically
            orphanRemoval = true,
            // FetchType.LAZY = don't load employees
            // until you actually access them
            // (better performance!)
            fetch = FetchType.LAZY
    )
    private List<Employee> employees =new ArrayList<>();

    public Department(String name, String location) {
        this.name = name;
        this.location = location;
    }

    // Helper method to add employee
    // keeps both sides in sync
    public void addEmployee(Employee employee) {
        employees.add(employee);
        // also set the department on employee side
        employee.setDepartment(this);
    }

    // Helper method to remove employee
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
        employee.setDepartment(null);
    }

    // Override toString to avoid infinite loop
    // (Department → Employee → Department → ...)
    @Override
    public String toString() {
        return "Department{id=" + id +
                ", name=" + name +
                ", location=" + location + "}";
    }
}
