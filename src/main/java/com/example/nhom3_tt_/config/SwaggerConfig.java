package com.example.nhom3_tt_.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  private SecurityScheme securityScheme() {
    return new SecurityScheme()
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
        .in(SecurityScheme.In.HEADER)
        .name("Authorization");
  }

  @Bean
  public OpenAPI myOpenAPI() {
    return new OpenAPI()
        .components(new Components().addSecuritySchemes("bearerAuth", securityScheme()))
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .info(new Info().title("LMS (Learning Management System)").version("1.0.0"));
  }
}
