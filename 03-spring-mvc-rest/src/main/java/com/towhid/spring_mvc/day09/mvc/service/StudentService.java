package com.towhid.spring_mvc.day09.mvc.service;

import com.towhid.spring_mvc.day09.mvc.dto.StudentRequest;
import com.towhid.spring_mvc.day09.mvc.dto.StudentResponse;
import com.towhid.spring_mvc.day09.mvc.entity.Student;
import com.towhid.spring_mvc.day09.mvc.repository.StudentRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    // ─────────────────────────────────────────
    // CONVERT Entity → Response DTO
    // ─────────────────────────────────────────
    private StudentResponse toResponse(Student student){
        // manually map entity fields to DTO fields
        StudentResponse response = new StudentResponse();
        response.setId(student.getId());
        response.setName(student.getName());
        response.setEmail(student.getEmail());
        response.setAge(student.getAge());
        response.setCourse(student.getCourse());
        response.setGrade(student.getGrade());
        response.setCreatedAt(student.getCreatedAt());
        return response;
        // notice: updatedAt NOT included - our choice!
    }

    // ─────────────────────────────────────────
    // CONVERT Request DTO → Entity
    // ─────────────────────────────────────────
    private Student toEntity(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());
        student.setCourse(request.getCourse());
        student.setGrade(request.getGrade());
        // no id set → DB will generate it
        return student;
    }

    // ─────────────────────────────────────────
    // CREATE
    // ─────────────────────────────────────────
    public StudentResponse createStudent(
            StudentRequest request) {

        // check duplicate email
        if (studentRepository.existsByEmail(
                request.getEmail())) {
            throw new RuntimeException(
                    "Email already exists: " + request.getEmail());
        }

        // convert DTO → Entity
        Student student = toEntity(request);

        // save to DB
        Student saved = studentRepository.save(student);

        // convert Entity → Response DTO
        return toResponse(saved);
    }

    // ─────────────────────────────────────────
    // GET ALL
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                // convert each entity to response DTO
                .map(this::toResponse)
                // collect into list
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // GET BY ID
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Integer id) {
        Student student = studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Student not found: " + id));
        return toResponse(student);
    }

    // ─────────────────────────────────────────
    // GET BY COURSE
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByCourse(
            String course) {
        return studentRepository
                .findByCourse(course)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    // ─────────────────────────────────────────
    // UPDATE
    // ─────────────────────────────────────────
    public StudentResponse updateStudent(
            Integer id, StudentRequest request) {

        // find existing student
        Student existing = studentRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Student not found: " + id));

        // update fields
        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setAge(request.getAge());
        existing.setCourse(request.getCourse());
        existing.setGrade(request.getGrade());

        // save updated entity
        Student updated = studentRepository.save(existing);

        return toResponse(updated);
    }

    // ─────────────────────────────────────────
    // DELETE
    // ─────────────────────────────────────────
    public void deleteStudent(Integer id) {
        // check exists first
        studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Student not found: " + id));

        studentRepository.deleteById(id);
    }

    // ─────────────────────────────────────────
    // Practice Task 1 -
    // GET /api/students/count
    //  → returns total number of students
    //  → Response: { "total": 5 }
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public long getStudentCount(){
        return studentRepository.count();
    }

    // ─────────────────────────────────────────
    // Practice Task 2 -
    // GET /api/students/top?grade=8.0
    //  → returns students with grade ABOVE value
    //  → uses @RequestParam
    // ─────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsWithGradesGreaterThan(Double grade){
        return studentRepository.findByGradeGreaterThan(grade)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


}
