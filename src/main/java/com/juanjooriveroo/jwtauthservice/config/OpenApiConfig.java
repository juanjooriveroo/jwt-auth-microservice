package com.juanjooriveroo.jwtauthservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("JWT Auth Service API")
                        .description("API para autenticación de usuarios con JWT")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Juan José Rivero Lorido")
                                .email("juanjoriverolorido@gmail.com")
                                .url("https://github.com/juanjooriveroo"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}