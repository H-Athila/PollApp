package com.pollapp.config; // <<< Uses the correct package name

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI multiPollOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("Dynamic Multi-Poll Application API")
                             .description("Endpoints for creating, listing, voting, and viewing results for multiple polls.")
                             .version("1.0.0")); // Set the version of your API
    }
}