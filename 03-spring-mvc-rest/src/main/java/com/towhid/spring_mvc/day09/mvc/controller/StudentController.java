package com.towhid.spring_mvc.day09.mvc.controller;

import com.towhid.spring_mvc.day09.mvc.dto.StudentRequest;
import com.towhid.spring_mvc.day09.mvc.dto.StudentResponse;
import com.towhid.spring_mvc.day09.mvc.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// @RestController = @Controller + @ResponseBody
// Every method returns DATA not a view/HTML
@RestController

// @RequestMapping = base URL for all methods
// All endpoints start with /api/students
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    // Constructor injection
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    // ─────────────────────────────────────────
    // CREATE — POST /api/students
    // ─────────────────────────────────────────
    @PostMapping
    // @RequestBody = read JSON from request body
    // Spring converts JSON → StudentRequest object
    public ResponseEntity<StudentResponse> createStudent(
           @Valid @RequestBody StudentRequest request) {
        StudentResponse response =
                studentService.createStudent(request);

        // 201 Created = new resource was created
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ─────────────────────────────────────────
    // GET ALL — GET /api/students
    // ─────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {
        List<StudentResponse> students =
                studentService.getAllStudents();
        // 200 OK with list of students
        return ResponseEntity.ok(students);
    }

    // ─────────────────────────────────────────
    // GET BY ID — GET /api/students/{id}
    // ─────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Integer id){
        StudentResponse response =
                studentService.getStudentById(id);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────
    // GET BY COURSE — GET /api/students?course=Java
    // ─────────────────────────────────────────
    @GetMapping("/search")
    // @RequestParam = gets ?course=Java from URL
    // GET /api/students/search?course=Java
    public ResponseEntity<List<StudentResponse>> getStudentsByCourse(
            @RequestParam String course){
        List<StudentResponse> students =
                studentService.getStudentsByCourse(course);

        return ResponseEntity.ok(students);
    }

    // ─────────────────────────────────────────
    // UPDATE — PUT /api/students/{id}
    // ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Integer id,
           @Valid @RequestBody StudentRequest request) {

        StudentResponse response =
                studentService.updateStudent(id, request);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────
    // DELETE — DELETE /api/students/{id}
    // ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @PathVariable Integer id) {

        studentService.deleteStudent(id);

        // 204 No Content = success but nothing to return
        return ResponseEntity.noContent().build();
    }


    // ─────────────────────────────────────────
    // Practice Task 1 -
    // GET /api/students/count
    //  → returns total number of students
    //  → Response: { "total": 5 }
    // ─────────────────────────────────────────
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getStudentsCount(){
        Long count = studentService.getStudentCount();
        Map<String, Long> response = new HashMap<>();
        response.put("total", count);
        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────
    // Practice Task 2 -
    // GET /api/students/top?grade=8.0
    //  → returns students with grade ABOVE value
    //  → uses @RequestParam
    // ─────────────────────────────────────────
    @GetMapping("/top")
    public ResponseEntity<List<StudentResponse>>
            getStudentsWithGradesGreaterThan(
                    @RequestParam Double grade){
        List<StudentResponse> students = studentService.getStudentsWithGradesGreaterThan(grade);
        return ResponseEntity.ok(students);
    }
}
