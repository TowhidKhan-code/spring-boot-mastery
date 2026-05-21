package com.towhid.spring_learning;

import com.towhid.spring_learning.day02.properties.AppValueProperties;
import com.towhid.spring_learning.day03.profiles.NotificationService.ProfileNotificationService;
import com.towhid.spring_learning.day03.properties.AppProperties;
import com.towhid.spring_learning.day03.profiles.ProfilePaymentService;
import com.towhid.spring_learning.day01.service.AlertService;
import com.towhid.spring_learning.day01.service.NotificationManager;
import com.towhid.spring_learning.day01.service.OrderService;
import com.towhid.spring_learning.day01.service.PaymentService;
import com.towhid.spring_learning.day02.config.AppConfig;
import com.towhid.spring_learning.day02.config.DatabaseConfig;
import com.towhid.spring_learning.day02.lifecycle.DatabaseConnectionService;
import com.towhid.spring_learning.day02.scope.ShoppingCart;
import com.towhid.spring_learning.day03.properties.DatabaseProperties;
import com.towhid.spring_learning.day03.properties.EmailProperties;
import com.towhid.spring_learning.day03.service.ConfigDashboardService;
import com.towhid.spring_learning.day04.autowiring.ServiceA;
import com.towhid.spring_learning.day04.config.ConnectionPool;
import com.towhid.spring_learning.day04.config.ResourceManager;
import com.towhid.spring_learning.day04.autowiring.AutowiringDemo;
import com.towhid.spring_learning.day04.service.BankService;
import com.towhid.spring_learning.day05.spel.config.SpelConfig;
import com.towhid.spring_learning.day05.spel.service.SpelBeanReferenceDemo;
import com.towhid.spring_learning.day05.spel.service.SpelCollectionDemo;
import com.towhid.spring_learning.day05.spel.service.SpelProgrammaticDemo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class SpringLearningApplication {

	public static void main(String[] args) {

		// This creates the IoC Container and returns it
		ApplicationContext context =
				SpringApplication.run(SpringLearningApplication.class, args);

		System.out.println("\n" + "=".repeat(50));
		System.out.println("🌱 SPRING IoC CONTAINER IS READY!");
		System.out.println("=".repeat(50));

		// Get OrderService from container
		// Spring already created it and injected EmailNotificationService!
		OrderService orderService = context.getBean(OrderService.class);

		orderService.placeOrder("MacBook Pro", 1999.99);
		orderService.placeOrder("iPhone 15", 999.99);

		PaymentService paymentService = context.getBean(PaymentService.class);
		paymentService.processPayment("ORD-001", 1999.99);

		// All beans Spring created:
		System.out.println("\n" + "=".repeat(50));
		System.out.println("📦 ALL BEANS IN IoC CONTAINER:");
		System.out.println("=".repeat(50));

		String[] beanNames = context.getBeanDefinitionNames();
		System.out.println("Total beans: " + beanNames.length);
		System.out.println("\nYOUR beans:");

		for (String beanName : beanNames) {
			// Show only YOUR beans (not Spring's internal beans)
			if (beanName.contains("towhid") ||
					beanName.contains("Notification") ||
					beanName.contains("Service")) {
				System.out.println("  → " + beanName);
			}
		}

		// Bean Singleton Proof:
		System.out.println("\n" + "=".repeat(50));
		System.out.println("SINGLETON PROOF:");
		System.out.println("=".repeat(50));

		// Get OrderService twice
		OrderService os1 = context.getBean(OrderService.class);
		OrderService os2 = context.getBean(OrderService.class);

		System.out.println("os1 == os2: " + (os1 == os2));
		// true! Same object! Spring returns the same instance every time!

		System.out.println("os1 hashCode: " + os1.hashCode());
		System.out.println("os2 hashCode: " + os2.hashCode());
		// Both will be SAME hashCode!

		AlertService alertService = context.getBean(AlertService.class);
		alertService.sendAlert("System is down!");


		// Day 1 - Practice
		NotificationManager notificationManager = context.getBean(NotificationManager.class);
		notificationManager.notifyUser("John","Your order is ready!");

		String[] beans2 = context.getBeanDefinitionNames();
		for (String beanName : beans2){
			if (beanName.contains("towhid") ||
					beanName.contains("Message") ||
					beanName.contains("Sender")) {
				System.out.println("  → " + beanName);
			}
		}


		// DAY 2
		// ==========================================
		// PROTOTYPE PROOF (ShoppingCart)
		// ==========================================
		System.out.println("\n=== PROTOTYPE SCOPE ===");

		ShoppingCart cart1 = context.getBean(ShoppingCart.class);
		ShoppingCart cart2 = context.getBean(ShoppingCart.class);
		ShoppingCart cart3 = context.getBean(ShoppingCart.class);

		System.out.println("Same object? " + (cart1 == cart2));
		System.out.println("cart1 ID: " + cart1.getCartId());
		System.out.println("cart2 ID: " + cart2.getCartId());
		System.out.println("cart3 ID: " + cart3.getCartId());

		cart1.addItem("Laptop");
		cart2.addItem("Phone");

		System.out.println("\n=== BEAN LIFECYCLE ===");
		DatabaseConnectionService dbService =
				context.getBean(DatabaseConnectionService.class);
		System.out.println("Current Status: " + dbService.getStatus());

		System.out.println("\n=== @Configuration BEANS ===");

		RestTemplate restTemplate = context.getBean(RestTemplate.class);
		System.out.println("RestTemplate: " + restTemplate);

		String appName = context.getBean("appName", String.class);
		System.out.println("App Name: " + appName);

		Integer maxRetries = context.getBean("maxRetries", Integer.class);
		System.out.println("Max Retries: " + maxRetries);

		AppValueProperties appProperties = context.getBean(AppValueProperties.class);
		appProperties.displayProperties();

		DatabaseConfig databaseConfig = context.getBean(DatabaseConfig.class);
		databaseConfig.display();

		AppConfig appConfig = context.getBean(AppConfig.class);
		System.out.println(appConfig.lang());

		System.out.println("\n=== USING CONFIG PROPERTIES ===");

		DatabaseProperties dbProps =
				context.getBean(DatabaseProperties.class);

		AppProperties appProps =
				context.getBean(AppProperties.class);

// Use the properties
		System.out.println("\nApp: " + appProps.getName()
				+ " v" + appProps.getVersion());

		System.out.println("DB: " + dbProps.getHost()
				+ ":" + dbProps.getPort());

		System.out.println("Debug mode: " + appProps.isDebug());

		EmailProperties emailProperties = context.getBean(EmailProperties.class);
		emailProperties.displayProperties();

		ConfigDashboardService dashBoard = context.getBean(ConfigDashboardService.class);
		dashBoard.printDashboard();

		System.out.println("\n===== DAY 3: Spring Profiles =====");
		ProfilePaymentService paymentService2 =
				context.getBean(ProfilePaymentService.class);
		System.out.println(paymentService2.processPayment(99.99));

		ProfileNotificationService profileNotificationService = context.getBean(ProfileNotificationService.class);
		profileNotificationService.sendNotification("Server restarted!");

		System.out.println("\n===== DAY 4: Java Configuration =====");

		String appNamee = context.getBean("applicationName",String.class);
		System.out.println("App Name: " + appNamee);

		ConnectionPool pool = context.getBean(ConnectionPool.class);
		System.out.println("Pool Active: " + pool.isActive());

		ResourceManager rm = context.getBean(ResourceManager.class);
		System.out.println("Resource Manager: " + rm.isInitialized());

		System.out.println("\n===== DAY 4: Autowiring =====");
		AutowiringDemo demo = context.getBean(AutowiringDemo.class);
		demo.demonstrateAutowiring();

		ServiceA serviceA = context.getBean(ServiceA.class);
		System.out.println("\nCircular Dependency solved:");
		System.out.println(serviceA.doSomething());

		System.out.println("\n===== DAY 4: AOP =====");

		BankService bankService = context.getBean(BankService.class);

		System.out.println("\n--- Test 1: Deposit ---");
		String depositResult = bankService.deposit("ACC001", 500.0);
		System.out.println("Result: " + depositResult);

		System.out.println("\n--- Test 2: Get Balance ---");
		double balance = bankService.getBalance("ACC001");
		System.out.println("Balance: $" + balance);

		System.out.println("\n--- Test 3: Exception ---");
		try {
			bankService.transfer("ACCO1","ACC02",9999.9);

			bankService.withdraw("ACC001", 99999.0);
		} catch (Exception e) {
			System.out.println("Caught: " + e.getMessage());
		}

		// ===== DAY 5: SpEL =====
		System.out.println("\n===== DAY 5: Spring Expression Language =====");


		SpelConfig spelConfig = context.getBean(SpelConfig.class);
		spelConfig.displayAll();

		SpelBeanReferenceDemo beanRef = context.getBean(SpelBeanReferenceDemo.class);
		beanRef.display();

		SpelCollectionDemo collDemo = context.getBean(SpelCollectionDemo.class);
		collDemo.display();

		SpelProgrammaticDemo progDemo = context.getBean(SpelProgrammaticDemo.class);
		progDemo.demonstrate();
	}
}
