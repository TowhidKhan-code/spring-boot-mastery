package com.towhid.spring_learning.day05.spel.service;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

// SpEL can also be used PROGRAMMATICALLY
// Without @Value — just pure Java code
// Useful when expression is DYNAMIC (not known at compile time)
@Component
public class SpelProgrammaticDemo {

    public void demonstrate() {
        System.out.println("\n====== SpEL PROGRAMMATIC DEMO ======");

        // Step 1: Create the parser
        // This is the engine that reads SpEL expressions
        ExpressionParser parser = new SpelExpressionParser();

        // ─────────────────────────────────
        // BASIC EXPRESSION
        // ─────────────────────────────────

        // Parse a simple math expression
        Expression exp1 = parser.parseExpression("100 * 2 + 50");
        // getValue() evaluates it and returns result
        Integer result1 = exp1.getValue(Integer.class);
        System.out.println("100 * 2 + 50 = " + result1);
        // Output: 250

        // String expression
        Expression exp2 = parser.parseExpression("'Hello SpEL'.toUpperCase()");
        String result2 = exp2.getValue(String.class);
        System.out.println("String result: " + result2);
        // Output: HELLO SPEL

        // ─────────────────────────────────
        // WITH CONTEXT (Variables)
        // ─────────────────────────────────

        // StandardEvaluationContext lets you
        // pass variables into SpEL expressions
        StandardEvaluationContext context = new StandardEvaluationContext();

        // Set variables using #variableName
        context.setVariable("salary", 50000.0);
        context.setVariable("taxRate", 0.20);

        // Use those variables in expression
        Expression exp3 = parser.parseExpression("#salary * (1 - #taxRate)");
        Double afterTax = exp3.getValue(context, Double.class);
        System.out.println("Salary after 20% tax: " + afterTax);
        // 50000 * 0.80 = 40000.0

        // ─────────────────────────────────
        // WITH OBJECT as ROOT
        // ─────────────────────────────────

        // You can pass any object as the ROOT
        // Then access its properties directly
        PersonData person = new PersonData("Towhid", 25);

        // Pass person as the root object
        Expression exp4 = parser.parseExpression("name");
        String name = exp4.getValue(person, String.class);
        System.out.println("Person name: " + name);
        // Accesses person.getName() → Towhid

        Expression exp5 = parser.parseExpression("age > 18 ? 'Adult' : 'Minor'");
        String category = exp5.getValue(person, String.class);
        System.out.println("Category: " + category);
        // 25 > 18 → Adult

        // ─────────────────────────────────
        // DYNAMIC EXPRESSION (Runtime)
        // ─────────────────────────────────

        // This is where programmatic SpEL shines
        // Expression can come from DB, user input, config file
        String dynamicExpression = "salary * 0.10";
        context.setVariable("salary", 75000.0);

        Expression dynExp = parser.parseExpression("#" + dynamicExpression);
        // Oops — this won't work with # like that
        // Let's do it properly:
        Expression dynExp2 = parser.parseExpression("#salary * 0.10");
        Double dynResult = dynExp2.getValue(context, Double.class);
        System.out.println("Dynamic bonus: " + dynResult);
        // 75000 * 0.10 = 7500.0
    }

    // Simple inner class for demo purposes
    // Acts as a root object for SpEL
    public static class PersonData {
        private String name;
        private int age;

        public PersonData(String name, int age) {
            this.name = name;
            this.age = age;
        }

        // SpEL needs getters to access properties
        public String getName() { return name; }
        public int getAge() { return age; }
    }
}