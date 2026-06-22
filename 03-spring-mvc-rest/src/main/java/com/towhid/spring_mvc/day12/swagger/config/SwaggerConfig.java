package com.towhid.spring_mvc.day12.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// This class customizes how your Swagger page looks
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        // title shown at the top of Swagger page
                        .title("Book Management API")

                        // version of your API
                        .version("1.0.0")

                        // description below the title
                        .description(
                                "REST API for managing books. " +
                                        "Supports CRUD, pagination, " +
                                        "sorting and search.")

                        // your contact info
                        .contact(new Contact()
                                .name("Towhid Islam")
                                .email("towhid@email.com"))

                        // license (optional)
                        .license(new License()
                                .name("MIT License"))
                );
    }
}