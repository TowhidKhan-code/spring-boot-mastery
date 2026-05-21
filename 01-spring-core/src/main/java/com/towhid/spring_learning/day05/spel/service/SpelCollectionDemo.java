package com.towhid.spring_learning.day05.spel.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// This shows SpEL working with
// Collections, Lists, Maps
@Component
public class SpelCollectionDemo {

    // ─────────────────────────────────────────
    // INLINE LIST using SpEL
    // ─────────────────────────────────────────

    @Value("#{{'Java','Spring','Docker','Kafka'}}")
    private List<String> techStack;
    // SpEL creates a list inline using {  } syntax

    @Value("#{{'Java','Spring','Docker','Kafka'}[0]}")
    private String firstTech;
    // Gets index 0 -> 'Java'

    @Value("#{{'Java', 'Spring', 'Docker', 'Kafka'}.size()}")
    private int techCount;
    // Calls .size() on the list → 4

    // ─────────────────────────────────────────
    // INLINE MAP using SpEL
    // ─────────────────────────────────────────

    @Value("#{{'name':'Towhid', 'role':'Developer', 'level':'Mid'}}")
    private Map<String, String> profileMap;
    // SpEL creates a map inline using { key:value } syntax

    @Value("#{{'name':'Towhid', 'role':'Developer'}['role']}")
    private String role;
    // Access map by key → 'Developer'

    // ─────────────────────────────────────────
    // COLLECTION FILTERING (Selection)
    // ─────────────────────────────────────────

    // .?[condition] → filters list keeping items that match
    @Value("#{{'Java','Spring','Docker','Kafka'}.?[length() > 4]}")
    private List<String> longNames;
    // Keeps only items where length > 4
    // 'Spring'(6) , 'Docker'(6) , 'Kafka'(5)
    // 'Java'(4) - Removed

    // .^[condition] → first matching element
    @Value("#{{'Java','Spring','Docker','Kafka'}.^[length() > 4]}")
    private String firstLongName;
    // First item with length > 4 → 'Spring'

    // .$[condition] → last matching element
    @Value("#{{'Java','Spring','Docker','Kafka'}.$[length() > 4]}")
    private String lastLongName;
    // Last item with length > 4 → 'Kafka'


    public void display() {
        System.out.println("\n====== SpEL COLLECTION DEMO ======");
        System.out.println("Tech Stack       : " + techStack);
        System.out.println("First Tech       : " + firstTech);
        System.out.println("Tech Count       : " + techCount);
        System.out.println("Profile Map      : " + profileMap);
        System.out.println("Role             : " + role);
        System.out.println("Long Names (>4)  : " + longNames);
        System.out.println("First Long Name  : " + firstLongName);
        System.out.println("Last Long Name   : " + lastLongName);
    }
}
