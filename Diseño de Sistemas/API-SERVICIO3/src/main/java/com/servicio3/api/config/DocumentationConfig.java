package com.servicio3.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DocumentationConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().
                info(new Info()
                        .title("Grupo12 - Servicio3 API")
                        .version("0.0.1")
                        .description("API del grupo 12 locoooooooo"));
    }
}
