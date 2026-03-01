package com.lifttrack.api.infrastructure.rest;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI liftTrackOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("LiftTrack API")
                        .version("1.0.0")
                        .description("REST API for LiftTrack — a fitness tracking application for managing routines, workout sessions, exercises, and personal records.")
                        .contact(new Contact()
                                .name("LiftTrack")));
    }
}
