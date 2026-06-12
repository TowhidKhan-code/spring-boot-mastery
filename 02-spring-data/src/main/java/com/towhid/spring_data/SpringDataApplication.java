package com.towhid.spring_data;

import com.towhid.spring_data.day06.jdbc.model.Course;
import com.towhid.spring_data.day06.jdbc.model.Student;
import com.towhid.spring_data.day06.jdbc.service.CourseService;
import com.towhid.spring_data.day06.jdbc.service.StudentService;
import com.towhid.spring_data.day07.jpa.entity.Category;
import com.towhid.spring_data.day07.jpa.entity.Product;
import com.towhid.spring_data.day07.jpa.repository.ProductRepository;
import com.towhid.spring_data.day07.jpa.service.CategoryService;
import com.towhid.spring_data.day07.jpa.service.ProductService;
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
		// ─────────────────────────────────────
		// DAY 6 - PRACTICE TASK
		// ─────────────────────────────────────

		// ─────────────────────────────────────
		// 1 - Add a new method findByGradeGreaterThan(Double grade) in repository and service
		// that returns all students with grade above a given value.
		// ─────────────────────────────────────
        List<Student> gradeGreaterThan = service.getGradeGreaterThan(8.0);
        gradeGreaterThan.forEach(s -> System.out.println(
                "  [" + s.getId() + "] " +
                        s.getName() + " | " +
                        s.getCourse() + " | Grade: " +
                        s.getGrade()
        ));

		// ─────────────────────────────────────
		// 2 - Add a method updateGrade(Integer id, Double newGrade) that:
		// - Checks student exists
		// - Only updates the grade column (not all fields)
		// - Returns updated student
		// ─────────────────────────────────────
		Student updatedGrade = service.updateGrade(1,9.9);
		System.out.println("  [" + updatedGrade.getId() + "] " +
				updatedGrade.getName() + " | " +
				updatedGrade.getCourse() + " | Grade: " +
				updatedGrade.getGrade() );

		// ─────────────────────────────────────
		// 3 - Create a completely new Course table:
		// With its own CourseRepository and CourseService — full CRUD.
		// ─────────────────────────────────────
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

		// ===== DAY 7: JPA + Hibernate =====
		System.out.println("\n" + "=".repeat(50));
		System.out.println("DAY 7 — JPA + Hibernate DEMO");
		System.out.println("=".repeat(50));

		ProductService productService = context.getBean(ProductService.class);

		// ── CREATE ──
		System.out.println("\n--- CREATE ---");
		Product p1 = productService.createProduct(
				"iPhone 15", "Apple smartphone",
				999.99, 50, "Electronics"
		);
		Product p2 = productService.createProduct(
				"Samsung TV", "55 inch 4K TV",
				799.99, 20, "Electronics"
		);
		Product p3 = productService.createProduct(
				"Java Book", "Core Java Programming",
				49.99, 100, "Books"
		);
		Product p4 = productService.createProduct(
				"Spring Book", "Spring Boot Mastery",
				59.99, 75, "Books"
		);
		Product p5 = productService.createProduct(
				"Laptop Stand", "Ergonomic stand",
				29.99, 200, "Accessories"
		);

		// ── READ ALL ──
		System.out.println("\n--- READ ALL ---");
		productService.getAll().forEach(p ->
				System.out.println(
						"  [" + p.getId() + "] " + p.getName()
								+ " | $" + p.getPrice()
								+ " | " + p.getCategory()
				)
		);

		// ── READ BY CATEGORY ──
		System.out.println("\n--- BY CATEGORY: Electronics ---");
		productService.getByCategory("Electronics")
				.forEach(p -> System.out.println(
						"  → " + p.getName() + " $" + p.getPrice()
				));

		// ── READ BY PRICE RANGE ──
		System.out.println("\n--- PRICE RANGE: $30 - $100 ---");
		productService.getByPriceRange(30.0, 100.0)
				.forEach(p -> System.out.println(
						"  → " + p.getName() + " $" + p.getPrice()
				));

		// ── SEARCH BY NAME ──
		System.out.println("\n--- SEARCH: 'Book' ---");
		productService.searchByName("Book")
				.forEach(p -> System.out.println(
						"  → " + p.getName()
				));

		// ── LOW STOCK ──
		System.out.println("\n--- LOW STOCK (< 30) ---");
		productService.getLowStockProducts(30)
				.forEach(p -> System.out.println(
						"  → " + p.getName()
								+ " | Stock: " + p.getStockQuantity()
				));

		// ── UPDATE ──
		System.out.println("\n--- UPDATE ---");
		productService.updateProduct(
				p1.getId(),
				"iPhone 15 Pro", "Apple Pro smartphone",
				1199.99, 45, "Electronics"
		);
		Product updated3 = productService.getById(p1.getId());
		System.out.println("Updated: " + updated3.getName()
				+ " $" + updated3.getPrice());

		// ── STATUS UPDATE ──
		System.out.println("\n--- DEACTIVATE PRODUCT ---");
		productService.updateStatus(p5.getId(), false);

		// ── STATS ──
		System.out.println("\n--- STATS ---");
		productService.printStats();

		// ── DELETE ──
		System.out.println("\n--- DELETE ---");
		productService.deleteProduct(p5.getId());

		// ── FINAL COUNT ──
		System.out.println("\n--- FINAL COUNT ---");
		System.out.println("Remaining: "
				+ productService.getAll().size());

		// ===== DAY 7: PRACTICE TASKS =====
		System.out.println("\n" + "=".repeat(50));
		System.out.println("DAY 7 — PRACTICE TASKS");
		System.out.println("=".repeat(50));

		// Get beans
		ProductRepository productRepo = context.getBean(ProductRepository.class);
		CategoryService categoryService = context.getBean(CategoryService.class);

		// ─────────────────────────────────────────
		// TASK 1: Derived Query Methods
		// Add these derived query methods to ProductRepository:
		//  1. Find all products where stockQuantity is greater than a value
		//  2. Find products by active = true ordered by price descending
		//  3. Count all active products
		// ─────────────────────────────────────────
		System.out.println("\n--- TASK 1a: Stock Greater Than 30 ---");
		productRepo.findByStockQuantityGreaterThan(30)
				.forEach(p -> System.out.println(
						"  → " + p.getName()
								+ " | Stock: " + p.getStockQuantity()
				));

		System.out.println("\n--- TASK 1b: Active Products By Price DESC ---");
		productRepo.findByActiveOrderByPriceDesc(true)
				.forEach(p -> System.out.println(
						"  → " + p.getName()
								+ " | $" + p.getPrice()
				));

		System.out.println("\n--- TASK 1c: Count Active Products ---");
		long activeCount = productRepo.countByActive(true);
		System.out.println("  Active products: " + activeCount);

		// ─────────────────────────────────────────
		// TASK 2: Create a new Entity Category:
		// fields: id, name, description
		// With full CRUD in repository and service using Spring Data JPA — no SQL written!
		// ─────────────────────────────────────────
		System.out.println("\n--- TASK 2: Category CRUD ---");

		// CREATE
		System.out.println("\n--- CREATE Categories ---");
		Category cat1 = categoryService.createCategory(
				"Electronics", "Electronic gadgets and devices"
		);
		Category cat2 = categoryService.createCategory(
				"Books", "Educational and fiction books"
		);
		Category cat3 = categoryService.createCategory(
				"Accessories", "Computer and phone accessories"
		);
		Category cat4 = categoryService.createCategory(
				"Clothing", "Men and women clothing"
		);

		// READ ALL
		System.out.println("\n--- ALL Categories ---");
		categoryService.getAll().forEach(c ->
				System.out.println(
						"  [" + c.getId() + "] "
								+ c.getName()
								+ " | " + c.getDescription()
				)
		);

		// READ BY ID
		System.out.println("\n--- GET BY ID ---");
		Category found4 = categoryService.getById(cat1.getId());
		System.out.println("  Found: " + found4);

		// READ BY NAME
		System.out.println("\n--- GET BY NAME ---");
		Category foundByName = categoryService.getByName("Books");
		System.out.println("  Found: " + foundByName);

		// SEARCH BY KEYWORD
		System.out.println("\n--- SEARCH: 'Elec' ---");
		categoryService.searchByKeyword("Elec")
				.forEach(c -> System.out.println(
						"  → " + c.getName()
				));

		// UPDATE
		System.out.println("\n--- UPDATE Category ---");
		categoryService.updateCategory(
				cat1.getId(),
				"Electronics & Gadgets",
				"Updated: all electronic items"
		);
		Category updated4 = categoryService.getById(cat1.getId());
		System.out.println("  Updated: " + updated4.getName()
				+ " | " + updated4.getDescription());

		// DELETE
		System.out.println("\n--- DELETE Category ---");
		categoryService.deleteCategory(cat4.getId());

		// FINAL COUNT
		System.out.println("\n--- REMAINING Categories ---");
		categoryService.getAll().forEach(c ->
				System.out.println("  → " + c.getName())
		);
		System.out.println("  Total: " + categoryService.getAll().size());

		// ─────────────────────────────────────────
		// TASK 2: Error Cases
		// ─────────────────────────────────────────
		System.out.println("\n--- ERROR CASES ---");

		// Duplicate name
		try {
			categoryService.createCategory(
					"Books", "Duplicate test"
			);
		} catch (RuntimeException e) {
			System.out.println("  ✅ Caught: " + e.getMessage());
		}

		// Not found
		try {
			categoryService.getById(9999);
		} catch (RuntimeException e) {
			System.out.println("  ✅ Caught: " + e.getMessage());
		}

		// Name not found
		try {
			categoryService.getByName("NonExistent");
		} catch (RuntimeException e) {
			System.out.println("  ✅ Caught: " + e.getMessage());
		}

		// ─────────────────────────────────────────
		// TASK 3: Write a JPQL query that:
		//	Finds all products where price is below average
		// ─────────────────────────────────────────
		System.out.println("\n--- TASK 3: Below Average Price ---");
		productRepo.findBelowAveragePrice()
				.forEach(p -> System.out.println(
						"  → " + p.getName()
								+ " | $" + p.getPrice()
				));
	}
	
	
}