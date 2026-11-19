package tn.esprit.refood.donations.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI donationsOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("ReFood - MS04 Dons et associations")
                .description("Relie les associations aux surplus gratuits")
                .version("1.0"));
    }
}
