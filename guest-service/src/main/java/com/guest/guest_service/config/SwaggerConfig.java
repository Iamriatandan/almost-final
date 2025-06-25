package com.guest.guest_service.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI guestServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Guest Service API")
                        .description("API documentation for Guest Service in Hotel Management System")
                        .version("1.0.0"));
    }
}
