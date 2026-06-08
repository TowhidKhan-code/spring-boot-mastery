package com.towhid.spring_data;

import com.towhid.spring_data.day06.jdbc.model.Course;
import com.towhid.spring_data.day06.jdbc.model.Student;
import com.towhid.spring_data.day06.jdbc.service.CourseService;
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

		// DAY 6 - PRACTICE TASK

		// 1 - Add a new method findByGradeGreaterThan(Double grade) in repository and service
		// that returns all students with grade above a given value.
        List<Student> gradeGreaterThan = service.getGradeGreaterThan(8.0);
        gradeGreaterThan.forEach(s -> System.out.println(
                "  [" + s.getId() + "] " +
                        s.getName() + " | " +
                        s.getCourse() + " | Grade: " +
                        s.getGrade()
        ));

		// 2 - Add a method updateGrade(Integer id, Double newGrade) that:
		// - Checks student exists
		// - Only updates the grade column (not all fields)
		// - Returns updated student
		Student updatedGrade = service.updateGrade(1,9.9);
		System.out.println("  [" + updatedGrade.getId() + "] " +
				updatedGrade.getName() + " | " +
				updatedGrade.getCourse() + " | Grade: " +
				updatedGrade.getGrade() );

		// 3 - Create a completely new Course table:
		// With its own CourseRepository and CourseService — full CRUD.
		CourseService courseService = context.getBean(CourseService.class);
		Course c1 = courseService.createCourse("Spring Boot",5);
		Course c2 = courseService.createCourse("JAVA",8);
		Course c3 = courseService.createCourse("PYTHON",6);
		Course c4 = courseService.createCourse("MYSQL",4);

		List<Course> allCourse = courseService.getAllCourses();
		allCourse.forEach(c -> System.out.println("  [" + c.getId() + "] " +
				c.getName() + " | " + c.getDurationWeeks()));

		Course found2 = courseService.getCourseById(c1.getId());
		System.out.println("Found: " + found2);

		List<Course> courseDuration =
				courseService.getCourseWithDurations(6);
		courseDuration.forEach(s ->
				System.out.println("  → " + s.getName())
		);

		Course found3 = courseService.getCourseByName("MYSQL");
		System.out.println("Found course with name: " + found3);

		courseService.updateCourse(s1.getId(), "SPRING BOOT ADVANCED",6);
		Course updated2 = courseService.getCourseById(c1.getId());
		System.out.println("Updated: " + updated2);

		courseService.deleteCourse(c3.getId());

		courseService.printStats();

		try{
			Course found4 = courseService.getCourseById(999);
		} catch (RuntimeException e) {
			System.out.println("Caught expected error " + e.getMessage());
		}

	}
}