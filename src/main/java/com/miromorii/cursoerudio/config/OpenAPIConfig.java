package com.miromorii.cursoerudio.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
        .title("REST API'S RESTful from 0 with Java, Spring boot, Kubernetes and Docker")
        .version("v1")
                .description("REST API'S RESTful from 0 with Java, Spring boot, Kubernetes and Docker")
                .termsOfService("https://x.com/mir0mori")
                .license(new License().name("Apache 2.0")
                        .url("https://x.com/mir0mori")));
    }
}
