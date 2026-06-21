package com.towhid.spring_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
		"com.towhid.spring_mvc.day09",
		"com.towhid.spring_mvc.day11.validation"
})
@EnableJpaRepositories(basePackages = {
		"com.towhid.spring_mvc.day09.mvc.repository",
		"com.towhid.spring_mvc.day11.validation.repository"
})
@EntityScan(basePackages = {
		"com.towhid.spring_mvc.day09.mvc.entity",
		"com.towhid.spring_mvc.day11.validation.entity"
})
public class SpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMvcApplication.class, args);
	}

}
