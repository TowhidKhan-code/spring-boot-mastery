package com.towhid.spring_learning.day05.spel.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

// This class shows SpEL used with @Value
// All the different types of SpEL expressions
@Component
public class SpelConfig {
    // ─────────────────────────────────────────
    // 1. LITERAL VALUES
    // ─────────────────────────────────────────

    @Value("#{42}")
    private int magicNumber;
    // SpEL evaluates 42 → injects integer 42

    @Value("#{3.14}")
    private double pi;
    // SpEL evaluates 3.14 → injects double

    @Value("#{'Spring Boot Mastery'}")
    private String courseName;
    // Single quotes for strings in SpEL!

    @Value("#{true}")
    private boolean isEnabled;

    // ─────────────────────────────────────────
    // 2. MATH OPERATIONS
    // ─────────────────────────────────────────

    @Value("#{50000 * 0.10}")
    private double bonus;
    // Spring calculates 50000 * 0.10 = 5000.0

    @Value("#{2 ^ 8}")
    private int powerOfTwo;
    // 2 to the power of 8 = 256

    @Value("#{(100 + 200) * 3}")
    private int complexMath;
    // Brackets work! = 900

    // ─────────────────────────────────────────
    // 3. STRING OPERATIONS
    // ─────────────────────────────────────────

    @Value("#{'towhid khan'.toUpperCase()}")
    private String upperName;
    // Calls Java String method → TOWHID KHAN

    @Value("#{'  hello  '.trim()}")
    private String trimmed;
    // Trims whitespace → 'hello'

    @Value("#{'hello'.length()}")
    private int nameLength;
    // Returns 5


    // ─────────────────────────────────────────
    // 4. STATIC METHOD with T()
    // ─────────────────────────────────────────

    @Value("#{T(java.lang.Math).PI}")
    private double mathPi;
    // Accesses static field Math.PI → 3.14159...

    @Value("#{T(java.lang.Math).sqrt(144)}")
    private double sqrtResult;
    // Calls static method Math.sqrt(144) → 12.0

    @Value("#{T(java.lang.Math).max(10, 20)}")
    private int maxValue;
    // Math.max(10,20) → 20

    // ─────────────────────────────────────────
    // 5. TERNARY & ELVIS
    // ─────────────────────────────────────────

    @Value("#{50000 > 30000 ? 'Senior' : 'Junior'}")
    private String level;
    // 50000 > 30000 is true → 'Senior'

    @Value("#{null ?: 'Default Value'}")
    private String withDefault;
    // null → so returns 'Default Value'


    // ─────────────────────────────────────────
    // 6. COMBINING @Value ${} AND #{} TOGETHER
    // ─────────────────────────────────────────

    @Value("#{'${spring.application.name}' + ' - SpEL Demo'}")
    private String appTitle;
    // First reads property, then SpEL appends string


    // ─────────────────────────────────────────
    // DISPLAY METHOD
    // ─────────────────────────────────────────

    public void displayAll() {
        System.out.println("\n========== SpEL CONFIG DEMO ==========");

        System.out.println("--- Literals ---");
        System.out.println("Magic Number : " + magicNumber);
        System.out.println("Pi           : " + pi);
        System.out.println("Course Name  : " + courseName);
        System.out.println("Is Enabled   : " + isEnabled);

        System.out.println("\n--- Math ---");
        System.out.println("Bonus        : " + bonus);
        System.out.println("2^8          : " + powerOfTwo);
        System.out.println("Complex Math : " + complexMath);

        System.out.println("\n--- Strings ---");
        System.out.println("Upper Name   : " + upperName);
        System.out.println("Trimmed      : " + trimmed);
        System.out.println("Name Length  : " + nameLength);

        System.out.println("\n--- Static / T() ---");
        System.out.println("Math.PI      : " + mathPi);
        System.out.println("sqrt(144)    : " + sqrtResult);
        System.out.println("max(10,20)   : " + maxValue);

        System.out.println("\n--- Ternary & Elvis ---");
        System.out.println("Level        : " + level);
        System.out.println("With Default : " + withDefault);

        System.out.println("\n--- Combined ---");
        System.out.println("App Title    : " + appTitle);
    }
}