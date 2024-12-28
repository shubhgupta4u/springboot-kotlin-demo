package springboot.api.demo.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme

@Configuration
class OpenApiConfig {

    @Bean
    @Primary
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Product API")
                    .description("API for managing products")
                    .version("v1.0.0")
                    .license(License().name("Apache 2.0").url("http://springdoc.org"))
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
            .components(
                Components()
                    .addSecuritySchemes(
                        "bearerAuth",
                        SecurityScheme()
                            .type(SecurityScheme.Type.HTTP)
                            .scheme("Bearer")
                            .bearerFormat("JWT")
                    )
            )
    }
}
