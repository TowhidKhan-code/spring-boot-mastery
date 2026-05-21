package com.towhid.spring_learning.day05.spel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// This class shows HOW SpEL can reference
// ANOTHER BEAN's properties and methods
@Component
public class SpelBeanReferenceDemo {

    // ─────────────────────────────────────────
    // REFERENCING ANOTHER BEAN
    // ─────────────────────────────────────────

    // 'employee' is the bean name (lowercase class name)
    // .name calls getName() on Employee bean

    @Value("#{employee.name}")
    private String employeeName;
    // Spring finds 'employee' bean → calls getName() → injects

    @Value("#{employee.salary}")
    private double employeeSalary;
    // Gets salary from Employee bean

    @Value("#{employee.salary * 1.20}")
    private double salaryAfterRaise;
    // Gets salary AND does math on it! 50000 * 1.20 = 60000

    @Value("#{employee.annualBonus}")
    private double bonus;
    // Calls getAnnualBonus() method on Employee bean

    @Value("#{employee.department.toUpperCase()}")
    private String dept;
    // Gets department string then calls toUpperCase() on it

    // ─────────────────────────────────────────
    // CONDITIONAL BASED ON BEAN PROPERTY
    // ─────────────────────────────────────────

    @Value("#{employee.active ? 'Working' : 'On Leave'}")
    private String status;
    // Reads isActive() from employee → true → 'Working'

    @Value("#{employee.age >= 18 ? 'Adult Employee' : 'Minor'}")
    private String ageCategory;
    // employee.age is 25 → 25 >= 18 → 'Adult Employee'


    public void display() {
        System.out.println("\n====== SpEL BEAN REFERENCE DEMO ======");
        System.out.println("Employee Name      : " + employeeName);
        System.out.println("Employee Salary    : " + employeeSalary);
        System.out.println("Salary After Raise : " + salaryAfterRaise);
        System.out.println("Annual Bonus       : " + bonus);
        System.out.println("Department         : " + dept);
        System.out.println("Status             : " + status);
        System.out.println("Age Category       : " + ageCategory);
    }
}
