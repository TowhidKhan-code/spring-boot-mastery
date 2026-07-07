package com.towhid.spring_mvc.day09.mvc.controller;

import com.towhid.spring_mvc.day09.mvc.dto.PagedResponse;
import com.towhid.spring_mvc.day09.mvc.dto.StudentRequest;
import com.towhid.spring_mvc.day09.mvc.dto.StudentResponse;
import com.towhid.spring_mvc.day09.mvc.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(name = "Students", description = "Student management APIs")

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
    @Operation(
            summary = "Create a new student",
            description = "Creates a new student with the provided details"
    )
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
    @Operation(
            summary = "Get all students",
            description = "Returns list of all students"
    )
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
    @Operation(
            summary = "Get student by ID",
            description = "Returns a single student by their ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponse> getStudentById(
            @Parameter(description = "Student ID to retrieve")
            @PathVariable Integer id){
        StudentResponse response =
                studentService.getStudentById(id);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────
    // GET BY COURSE — GET /api/students?course=Java
    // ─────────────────────────────────────────
    @Operation(
            summary = "Search students by course",
            description = "Returns students enrolled in a specific course"
    )
    @GetMapping("/search")
    // @RequestParam = gets ?course=Java from URL
    // GET /api/students/search?course=Java
    public ResponseEntity<List<StudentResponse>> getStudentsByCourse(
            @Parameter(description = "Course name to search for")
            @RequestParam String course){
        List<StudentResponse> students =
                studentService.getStudentsByCourse(course);

        return ResponseEntity.ok(students);
    }

    // ─────────────────────────────────────────
    // UPDATE — PUT /api/students/{id}
    // ─────────────────────────────────────────
    @Operation(
            summary = "Update a student",
            description = "Updates an existing student by ID"
    )
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponse> updateStudent(
            @Parameter(description = "Student ID to update")
            @PathVariable Integer id,
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response =
                studentService.updateStudent(id, request);

        return ResponseEntity.ok(response);
    }

    // ─────────────────────────────────────────
    // DELETE — DELETE /api/students/{id}
    // ─────────────────────────────────────────
    @Operation(
            summary = "Delete a student",
            description = "Deletes a student by ID"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(
            @Parameter(description = "Student ID to delete")
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
    @Operation(
            summary = "Get student count",
            description = "Returns total number of students"
    )
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
    @Operation(
            summary = "Get top students by grade",
            description = "Returns students with grade above the specified value"
    )
    @GetMapping("/top")
    public ResponseEntity<List<StudentResponse>>
            getStudentsWithGradesGreaterThan(
                    @Parameter(description = "Minimum grade to filter by")
                    @RequestParam Double grade){
        List<StudentResponse> students = studentService.getStudentsWithGradesGreaterThan(grade);
        return ResponseEntity.ok(students);
    }

    // ─────────────────────────────────────────
    // DAY 12
    // TASK 2 :
    // ─────────────────────────────────────────
    @Operation(
            summary = "Get all students (paginated)",
            description = "Returns paginated list of students with sorting"
    )
    @GetMapping("/paged")
    public ResponseEntity<PagedResponse<StudentResponse>>
    getAllStudentsPaginated(
            @Parameter(description = "Page number (0-based)")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Items per page")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Sort by field name")
            @RequestParam(defaultValue = "id") String sortBy,

            @Parameter(description = "Sort direction: asc or desc")
            @RequestParam(defaultValue = "asc") String direction) {

        PagedResponse<StudentResponse> students =
                studentService.getAllStudentsPaginated(
                        page, size, sortBy, direction);

        return ResponseEntity.ok(students);
    }
}
