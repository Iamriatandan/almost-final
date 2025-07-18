package com.reservation.reservationservice.openapi;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI reservationServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Reservation Service API")
                        .description("API documentation for the Reservation Service in Hotel Management System")
                        .version("1.0"));
    }
}
