package com.towhid.spring_data.day06.jdbc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private Integer id;
    private String name;
    private Integer durationWeeks;

    public Course(String name, Integer durationWeeks) {
        this.name = name;
        this.durationWeeks = durationWeeks;
    }
}
