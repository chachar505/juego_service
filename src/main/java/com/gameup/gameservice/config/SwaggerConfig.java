package com.gameup.gameservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Juego Service API")
                        .version("1.0")
                        .description("Documentación del microservicio de juegos - GameUp"));
    }

    @Bean
    public GroupedOpenApi juegosApi() {
        return GroupedOpenApi.builder()
                .group("juegos")
                .pathsToMatch("/api/juegos/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoriasApi() {
        return GroupedOpenApi.builder()
                .group("categorias")
                .pathsToMatch("/api/categorias/**")
                .build();
    }
}