package vn.iotstar.productcatalog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    OpenAPI catalogOpenApi() {
        return new OpenAPI().info(new Info().title("Product & Category CRUD API").version("1.0")
                .description("REST API used by the AJAX catalogue screen."));
    }
}
