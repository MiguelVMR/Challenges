package com.agenda.application.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Class SwaggerConfiguration
 *
 * @author Miguel Vilela Moraes Ribeiro
 * @since 14/08/2024
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(this.info());
    }

    private Info info() {
        return new Info()
                .title("Commitment Project")
                .description("Project for commitment management")
                .version("1.0");
    }
}
