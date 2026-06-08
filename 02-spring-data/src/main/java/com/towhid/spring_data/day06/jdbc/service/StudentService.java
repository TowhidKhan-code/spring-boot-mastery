package com.towhid.spring_data.day06.jdbc.service;

import com.towhid.spring_data.day06.jdbc.model.Student;
import com.towhid.spring_data.day06.jdbc.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service = business logic layer
// Sits between main/controller and repository
@Service
public class StudentService {

    // Depends on repository interface
    // NOT the implementation — good practice!
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    public Student createStudent(String name, String email,
                                 Integer age, String course,
                                 Double grade) {

        // Basic validation before saving
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email");
        }

        // Create Student object (no id yet)
        Student student = new Student(name, email, age, course, grade);

        // Save to DB — returns student WITH id
        Student saved = studentRepository.save(student);

        System.out.println("✅ Student created with ID: " + saved.getId());
        return saved;
    }

    // ─────────────────────────────────────────
    // READ - single
    // ─────────────────────────────────────────
    public Student getStudentById(Integer id) {

        // Optional.orElseThrow() = get value OR throw exception
        return studentRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Student not found with id: " + id)
                );
    }

    // ─────────────────────────────────────────
    // READ - all
    // ─────────────────────────────────────────
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // ─────────────────────────────────────────
    // READ - by course
    // ─────────────────────────────────────────
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    public Student updateStudent(Integer id, String name,
                                 String email, Integer age,
                                 String course, Double grade) {

        // First check if student exists
        Student existing = getStudentById(id);
        // throws exception if not found

        // Update fields
        existing.setName(name);
        existing.setEmail(email);
        existing.setAge(age);
        existing.setCourse(course);
        existing.setGrade(grade);

        // Save changes to DB
        boolean success = studentRepository.update(existing);

        if (!success) {
            throw new RuntimeException("Update failed for id: " + id);
        }

        System.out.println("✅ Student updated: " + id);
        return existing;
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    public void deleteStudent(Integer id) {

        // Check exists first
        getStudentById(id);

        boolean success = studentRepository.deleteById(id);

        if (!success) {
            throw new RuntimeException("Delete failed for id: " + id);
        }

        System.out.println("✅ Student deleted: " + id);
    }

    // ─────────────────────────────────────────
    // STATS
    // ─────────────────────────────────────────
    public void printStats() {
        int total = studentRepository.count();
        System.out.println("📊 Total students in DB: " + total);
    }
}