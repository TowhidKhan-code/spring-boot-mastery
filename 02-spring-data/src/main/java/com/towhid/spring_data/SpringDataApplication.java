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
import com.towhid.spring_data.day08.relationships.entity.*;
import com.towhid.spring_data.day08.relationships.service.BankTransferService;
import com.towhid.spring_data.day08.relationships.service.EmployeeService;
import com.towhid.spring_data.day08.relationships.service.OrderService;
import com.towhid.spring_data.day08.relationships.service.ProductTagService;
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
		// With full CRUD in repository and service
		// using Spring Data JPA — no SQL written!
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
			System.out.println(" Caught: " + e.getMessage());
		}

		// Not found
		try {
			categoryService.getById(9999);
		} catch (RuntimeException e) {
			System.out.println(" Caught: " + e.getMessage());
		}

		// Name not found
		try {
			categoryService.getByName("NonExistent");
		} catch (RuntimeException e) {
			System.out.println(" Caught: " + e.getMessage());
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

		// ===== DAY 8: JPA Relationships + Transactions =====
		System.out.println("\n" + "=".repeat(50));
		System.out.println("DAY 8 — JPA Relationships + Transactions");
		System.out.println("=".repeat(50));

		EmployeeService employeeService =
				context.getBean(EmployeeService.class);
		OrderService orderService =
				context.getBean(OrderService.class);

		// ── DEPARTMENT + EMPLOYEE (OneToMany) ──
		System.out.println("\n--- DEPARTMENTS & EMPLOYEES ---");

		Department engineering = employeeService
				.createDepartment("Engineering", "Floor 3");
		Department marketing = employeeService
				.createDepartment("Marketing", "Floor 1");

		employeeService.addEmployeeToDepartment(
				engineering.getId(),
				"Towhid Khan", "towhid@company.com",
				"Senior Developer", 85000.0);
		employeeService.addEmployeeToDepartment(
				engineering.getId(),
				"Alice Smith", "alice@company.com",
				"Developer", 70000.0);
		employeeService.addEmployeeToDepartment(
				marketing.getId(),
				"Bob Johnson", "bob@company.com",
				"Marketing Manager", 75000.0);

		// Print department with employees
		Department engWithEmps = employeeService
				.getDepartmentWithEmployees(engineering.getId());
		System.out.println("\nDepartment: " + engWithEmps.getName());
		engWithEmps.getEmployees().forEach(e ->
				System.out.println("  → " + e));

		// ── TRANSFER EMPLOYEE ──
		System.out.println("\n--- TRANSFER EMPLOYEE ---");
		Employee towhid = engWithEmps.getEmployees().get(0);
		employeeService.transferEmployee(
				towhid.getId(),
				engineering.getId(),
				marketing.getId());

		// ── GIVE RAISE ──
		System.out.println("\n--- GIVE 10% RAISE ---");
		employeeService.giveRaiseToAllInDepartment(
				engineering.getId(), 10.0);

		// ── ROLLBACK DEMO ──
		System.out.println("\n--- ROLLBACK DEMO ---");
		try {
			employeeService.demonstrateRollback(
					engineering.getId());
		} catch (RuntimeException e) {
			System.out.println("Caught: " + e.getMessage());
			System.out.println("Transaction rolled back!");
		}

		// ── ORDERS (Nested Relationships) ──
		System.out.println("\n--- CUSTOMERS & ORDERS ---");

		Customer customer1 = orderService.createCustomer(
				"John Doe", "john@email.com", "555-0101");
		Customer customer2 = orderService.createCustomer(
				"Jane Smith", "jane@email.com", "555-0102");

		// Place order with items
		Order order1 = orderService.placeOrder(
				customer1.getId(),
				List.of(
						new OrderItem("iPhone 15", 1, 999.99),
						new OrderItem("Phone Case", 2, 19.99),
						new OrderItem("Screen Protector", 1, 9.99)
				));

		Order order2 = orderService.placeOrder(
				customer1.getId(),
				List.of(
						new OrderItem("MacBook Pro", 1, 1999.99)
				));

		Order order3 = orderService.placeOrder(
				customer2.getId(),
				List.of(
						new OrderItem("Spring Boot Book", 2, 49.99)
				));

		// ── UPDATE ORDER STATUS ──
		System.out.println("\n--- UPDATE ORDER STATUS ---");
		orderService.updateOrderStatus(
				order1.getId(),
				Order.OrderStatus.CONFIRMED);
		orderService.updateOrderStatus(
				order1.getId(),
				Order.OrderStatus.SHIPPED);

		// ── CANCEL ORDER ──
		System.out.println("\n--- CANCEL ORDER ---");
		orderService.cancelOrder(order3.getId());

		// ── GET CUSTOMER WITH ORDERS ──
		System.out.println("\n--- CUSTOMER WITH ORDERS ---");
		Customer c = orderService.getCustomerWithOrders(
				customer1.getId());
		System.out.println("Customer: " + c.getName());
		System.out.println("Orders: " + c.getOrders().size());
		c.getOrders().forEach(o ->
				System.out.println("  → " + o));

		// ── ORDERS BY STATUS ──
		System.out.println("\n--- SHIPPED ORDERS ---");
		orderService.getOrdersByStatus(Order.OrderStatus.SHIPPED)
				.forEach(o -> System.out.println(
						"  → " + o.getOrderNumber()
								+ " $" + o.getTotalAmount()));

		// ===== DAY 8: PRACTICE =====

		// ─────────────────────────────────────────
		// Task 1 - Add a method getHighEarners(Double minSalary)
		// in EmployeeService that returns all employees
		// earning above the given salary.
		// Print their name, position and salary.
		// ─────────────────────────────────────────

		System.out.println("\n--- HIGH EARNERS (> $70000) ---");

		List<Employee> highEarners = employeeService.getHighEarners(70000.0);

		highEarners.forEach(e ->
				System.out.println(
						"  → " + e.getName()
								+ " | " + e.getPosition()
								+ " | $" + e.getSalary()
				)
		);

		// ─────────────────────────────────────────
		// Task 2 - Add a ManyToMany relationship:
		//   - Create a Tag entity with id and name
		//   - A Product (from Day 7) can have many Tags
		//   - A Tag can belong to many Products
		//   - Add method to tag a product
		// ─────────────────────────────────────────


		System.out.println("\n" + "=".repeat(50));
		System.out.println("DAY 8 PRACTICE — Product ↔ Tag (ManyToMany)");
		System.out.println("=".repeat(50));

		ProductTagService tagService =
				context.getBean(ProductTagService.class);

		// Make sure you have some products
		// If not, create them first:

		// Create products if needed (from Day 7)
		// If products already exist, skip this part
		/*
		productService.createProduct(
			"iPhone 15", "Apple phone",
			999.99, 50, "Electronics");
		productService.createProduct(
			"MacBook", "Apple laptop",
			1999.99, 30, "Electronics");
		productService.createProduct(
			"Spring Book", "Learn Spring",
			49.99, 100, "Books");
		*/

		// ── TAG PRODUCTS ──
		System.out.println("\n--- Tagging Products ---");
		tagService.tagProduct(1, "Electronics");
		tagService.tagProduct(1, "Apple");
		tagService.tagProduct(1, "Mobile");

		tagService.tagProduct(2, "Electronics");
		tagService.tagProduct(2, "Apple");
		tagService.tagProduct(2, "Laptop");

		tagService.tagProduct(3, "Books");
		tagService.tagProduct(3, "Programming");

		// ── DUPLICATE TAG (should skip) ──
		System.out.println("\n--- Duplicate Tag Test ---");
		tagService.tagProduct(1, "Apple");  // already tagged!

		// ── VIEW PRODUCT TAGS ──
		System.out.println("\n--- Product 1 Tags ---");
		tagService.printProductTags(1);

		System.out.println("\n--- Product 2 Tags ---");
		tagService.printProductTags(2);

		// ── VIEW PRODUCTS BY TAG ──
		System.out.println("\n--- Products with 'Apple' tag ---");
		tagService.printTagProducts("Apple");

		System.out.println("\n--- Products with 'Electronics' tag ---");
		tagService.printTagProducts("Electronics");

		// ── REMOVE TAG ──
		System.out.println("\n--- Remove 'Mobile' from Product 1 ---");
		tagService.removeTag(1, "Mobile");
		tagService.printProductTags(1);

		// ── ERROR CASE ──
		System.out.println("\n--- Error Cases ---");
		try {
			tagService.tagProduct(9999, "Test");
		} catch (RuntimeException e) {
			System.out.println("✅ Caught: " + e.getMessage());
		}

		try {
			tagService.removeTag(1, "NonExistent");
		} catch (RuntimeException e) {
			System.out.println("✅ Caught: " + e.getMessage());
		}

		// ─────────────────────────────────────────
		// Task 3 - Implement a BankTransferService with a
		// 			method transfer(fromAccountId,
		// 			toAccountId, amount) that:
		//  - Deducts amount from one account
		//  - Adds to another account
		//  - Is fully @Transactional
		//  - Throws exception if balance insufficient
		//  - Proves rollback works if transfer fails midway
		// ─────────────────────────────────────────

		System.out.println("\n" + "=".repeat(50));
		System.out.println("DAY 8 PRACTICE — Bank Transfer (Transactions)");
		System.out.println("=".repeat(50));

		BankTransferService bankService =
				context.getBean(BankTransferService.class);

		// ── CREATE ACCOUNTS ──
		System.out.println("\n--- Creating Accounts ---");
		Account alice = bankService.createAccount(
				"ACC001", "Alice", 1000.0);
		Account bob = bankService.createAccount(
				"ACC002", "Bob", 500.0);
		Account carol = bankService.createAccount(
				"ACC003", "Carol", 750.0);

		// ── PRINT INITIAL BALANCES ──
		bankService.printBalances(
				alice.getId(), bob.getId(), carol.getId());

		// ── DEPOSIT ──
		System.out.println("\n--- Deposit Test ---");
		bankService.deposit(bob.getId(), 200.0);
		bankService.printBalances(bob.getId());

		// ── WITHDRAW ──
		System.out.println("\n--- Withdraw Test ---");
		bankService.withdraw(alice.getId(), 100.0);
		bankService.printBalances(alice.getId());

		// ── SUCCESSFUL TRANSFER ──
		System.out.println("\n--- Successful Transfer ---");
		System.out.println("Before transfer:");
		bankService.printBalances(alice.getId(), bob.getId());

		bankService.transfer(alice.getId(), bob.getId(), 200.0);

		System.out.println("\nAfter transfer:");
		bankService.printBalances(alice.getId(), bob.getId());

		// ── INSUFFICIENT BALANCE ──
		System.out.println("\n--- Insufficient Balance Test ---");
		try {
			bankService.transfer(alice.getId(), bob.getId(), 99999.0);
		} catch (Exception e) {
			System.out.println(" Caught: " + e.getMessage());
		}

		// Verify balances unchanged
		System.out.println("Balances after failed transfer:");
		bankService.printBalances(alice.getId(), bob.getId());

		// ══════════════════════════════════════════════════
		// ROLLBACK DEMO — Most Important Test!
		// ══════════════════════════════════════════════════
		System.out.println("\n" + "=".repeat(50));
		System.out.println(" ROLLBACK DEMONSTRATION");
		System.out.println("=".repeat(50));

		System.out.println("\n BEFORE transfer with failure:");
		bankService.printBalances(alice.getId(), carol.getId());

		// Store original balances for comparison
		Account aliceBefore = bankService.getAccount(alice.getId());
		Account carolBefore = bankService.getAccount(carol.getId());
		Double aliceOriginal = aliceBefore.getBalance();
		Double carolOriginal = carolBefore.getBalance();

		System.out.println("\nAttempting transfer of $300 from Alice to Carol...");
		System.out.println("(This will fail AFTER deducting from Alice!)");

		try {
			bankService.transferWithFailure(
					alice.getId(), carol.getId(), 300.0);
		} catch (Exception e) {
			System.out.println("\n Exception caught: " + e.getMessage());
		}

		System.out.println("\n AFTER failed transfer:");
		bankService.printBalances(alice.getId(), carol.getId());

		// Verify rollback worked
		Account aliceAfter = bankService.getAccount(alice.getId());
		Account carolAfter = bankService.getAccount(carol.getId());

		System.out.println("\n🔍 ROLLBACK VERIFICATION:");
		System.out.println("  Alice original: $" + aliceOriginal);
		System.out.println("  Alice after   : $" + aliceAfter.getBalance());
		System.out.println("  Alice restored: "
				+ (aliceOriginal.equals(aliceAfter.getBalance())
				? " YES!" : " NO!"));

		System.out.println("\n  Carol original: $" + carolOriginal);
		System.out.println("  Carol after   : $" + carolAfter.getBalance());
		System.out.println("  Carol unchanged: "
				+ (carolOriginal.equals(carolAfter.getBalance())
				? " YES!" : " NO!"));

		if (aliceOriginal.equals(aliceAfter.getBalance())
				&& carolOriginal.equals(carolAfter.getBalance())) {
			System.out.println("\n TRANSACTION ROLLBACK WORKED PERFECTLY!");
			System.out.println("   Both accounts are in their original state!");
		} else {
			System.out.println("\n ROLLBACK DID NOT WORK!");
			System.out.println("   Check @Transactional annotation!");
		}

		// ── ERROR CASES ──
		System.out.println("\n--- Other Error Cases ---");

		// Transfer to same account
		try {
			bankService.transfer(alice.getId(), alice.getId(), 100.0);
		} catch (Exception e) {
			System.out.println("Same account: " + e.getMessage());
		}

		// Negative amount
		try {
			bankService.transfer(alice.getId(), bob.getId(), -50.0);
		} catch (Exception e) {
			System.out.println("Negative amount: " + e.getMessage());
		}

		// Account not found
		try {
			bankService.transfer(9999, bob.getId(), 100.0);
		} catch (Exception e) {
			System.out.println("Not found: " + e.getMessage());
		}

	}
}