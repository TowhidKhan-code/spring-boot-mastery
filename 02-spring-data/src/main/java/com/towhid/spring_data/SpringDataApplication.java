package com.towhid.spring_data;

import com.towhid.spring_data.day06.jdbc.model.Student;
import com.towhid.spring_data.day06.jdbc.service.StudentService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class SpringDataApplication {

	public static void main(String[] args) {

		ApplicationContext context =
				SpringApplication.run(SpringDataApplication.class, args);

		StudentService service = context.getBean(StudentService.class);

		System.out.println("\n" + "=".repeat(50));
		System.out.println("DAY 6 — Spring JDBC DEMO");
		System.out.println("=".repeat(50));

		// ─────────────────────────────────────
		// CREATE students
		// ─────────────────────────────────────
		System.out.println("\n--- CREATE ---");
		Student s1 = service.createStudent(
				"Towhid Khan", "towhid@email.com",
				25, "Spring Boot", 9.5
		);
		Student s2 = service.createStudent(
				"Alice Smith", "alice@email.com",
				22, "Spring Boot", 8.7
		);
		Student s3 = service.createStudent(
				"Bob Johnson", "bob@email.com",
				24, "Java Basics", 7.5
		);
		Student s4 = service.createStudent(
				"Carol White", "carol@email.com",
				23, "Java Basics", 8.0
		);

		// ─────────────────────────────────────
		// READ - all
		// ─────────────────────────────────────
		System.out.println("\n--- READ ALL ---");
		List<Student> all = service.getAllStudents();
		all.forEach(s -> System.out.println(
				"  [" + s.getId() + "] " +
						s.getName() + " | " +
						s.getCourse() + " | Grade: " +
						s.getGrade()
		));

		// ─────────────────────────────────────
		// READ - by id
		// ─────────────────────────────────────
		System.out.println("\n--- READ BY ID ---");
		Student found = service.getStudentById(s1.getId());
		System.out.println("Found: " + found);

		// ─────────────────────────────────────
		// READ - by course
		// ─────────────────────────────────────
		System.out.println("\n--- READ BY COURSE ---");
		List<Student> springStudents =
				service.getStudentsByCourse("Spring Boot");
		springStudents.forEach(s ->
				System.out.println("  → " + s.getName())
		);

		// ─────────────────────────────────────
		// UPDATE
		// ─────────────────────────────────────
		System.out.println("\n--- UPDATE ---");
		service.updateStudent(
				s1.getId(),
				"Towhid Updated",
				"towhid.new@email.com",
				26, "Spring Boot Advanced", 9.8
		);
		Student updated = service.getStudentById(s1.getId());
		System.out.println("Updated: " + updated);

		// ─────────────────────────────────────
		// DELETE
		// ─────────────────────────────────────
		System.out.println("\n--- DELETE ---");
		service.deleteStudent(s4.getId());

		// ─────────────────────────────────────
		// STATS
		// ─────────────────────────────────────
		System.out.println("\n--- STATS ---");
		service.printStats();

		// ─────────────────────────────────────
		// ERROR CASE - not found
		// ─────────────────────────────────────
		System.out.println("\n--- ERROR CASE ---");
		try {
			service.getStudentById(9999);
		} catch (RuntimeException e) {
			System.out.println("✅ Caught expected error: "
					+ e.getMessage());
		}
	}
}