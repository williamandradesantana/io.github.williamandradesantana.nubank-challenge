package io.github.williamandradesantana.nubank_challenge.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                    .title("Nubank Challenge API")
                    .description("API de gerenciamento de clientes e contatos")
                    .version("v1"));
    }
}
