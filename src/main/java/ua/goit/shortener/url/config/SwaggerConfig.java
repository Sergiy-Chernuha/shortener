package ua.goit.shortener.url.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig  {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("URL shortener app API")
                        .version("v 1.0.0")
                        .description("A URL Shortener project."))
                .servers(List.of(new Server().url("http://localhost:9999")
                        .description("Dev service")));

    }
}