package com.example.demo.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Example API")
                    .version("1.0.0")
                    .description("A Kotlin Spring Boot service with PostgreSQL")
                    .contact(
                        Contact()
                            .name("Example Team")
                            .email("demo@example.com")
                    )
            )
    }
}
