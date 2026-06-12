package com.towhid.spring_data.day07.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name", nullable=false,length=200,unique = true)
    private String name;

    @Column(nullable=false)
    private String description;

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
