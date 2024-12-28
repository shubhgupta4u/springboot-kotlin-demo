package springboot.api.demo.filters

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import springboot.api.demo.util.GlobalAccessDeniedHandler
import springboot.api.demo.util.CustomJwtAuthenticationConverter

@Configuration
@EnableMethodSecurity // Enables @PreAuthorize and other method-level security
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity, customAccessDeniedHandler: GlobalAccessDeniedHandler): SecurityFilterChain {
        http
            .csrf().disable()
            .authorizeHttpRequests {
                it.requestMatchers("/oauth/**","/favicon.ico","/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui/**").permitAll() // Open auth endpoints
                    .anyRequest().authenticated() // Secure all other endpoints
            }
            .headers { headers ->
                headers.frameOptions().sameOrigin()  // Allow embedding for same origin (for Swagger UI)
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { jwt ->
                    jwt.jwtAuthenticationConverter(CustomJwtAuthenticationConverter())
                }
            }             
            .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)           
        return http.build()
    }
}
