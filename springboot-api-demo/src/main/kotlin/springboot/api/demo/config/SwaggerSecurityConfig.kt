// package springboot.api.demo.config

// import io.swagger.v3.oas.models.Components
// import io.swagger.v3.oas.models.OpenAPI
// import io.swagger.v3.oas.models.security.SecurityRequirement
// import io.swagger.v3.oas.models.security.SecurityScheme
// import org.springframework.context.annotation.Bean
// import org.springframework.context.annotation.Configuration

// @Configuration
// class SwaggerSecurityConfig {

//     @Bean
//     fun api(): OpenAPI {
//         return OpenAPI()
//             .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
//             .components(
//                 Components()
//                     .addSecuritySchemes(
//                         "bearerAuth",
//                         SecurityScheme()
//                             .type(SecurityScheme.Type.HTTP)
//                             .scheme("Bearer")
//                             .bearerFormat("JWT")
//                     )
//             )
//     }
// }
