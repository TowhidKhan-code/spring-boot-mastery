package com.towhid.spring_data.day08.relationships.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "employees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String email;
    private String position;
    private Double salary;

    // MANY Employees belong to ONE Department
    // @ManyToOne = this is the "owning" side
    // The owning side has the foreign key column!
    @ManyToOne(
            // LAZY = don't load department data
            // until you actually call getDeprtment()
            fetch = FetchType.LAZY
    )
    // @JoinColumn defines the foreign key column
    // name = column name in employees table
    @JoinColumn(
            name = "department_id",
            // nullable = false → every employee
            // must belong to a department
            nullable = false
    )
    private Department department;

    public Employee(String name, String email,
                    String position, Double salary) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }

    // Override toString to avoid infinite loop
    @Override
    public String toString() {
        return "Employee{id=" + id +
                ", name=" + name +
                ", position=" + position +
                ", salary=" + salary + "}";
    }
}

