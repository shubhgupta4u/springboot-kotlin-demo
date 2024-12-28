package springboot.api.demo.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import springboot.api.demo.services.JwtService
import springboot.api.demo.models.AuthRequest

@RestController
@RequestMapping("/oauth")
@Tag(name = "Authentication", description = "Endpoints for managing authentication")
class AuthController(private val jwtService: JwtService) {

    @PostMapping("/token")
    @Operation(summary = "Create Token", description = "Create a new JWT token")
    fun createToken(@RequestBody authRequest: AuthRequest): ResponseEntity<Any> {
        // Replace with actual authentication logic
        if (authRequest.username == "admin" && authRequest.password == "password") {
            val token = jwtService.createToken(authRequest.username, listOf("ROLE_ADMIN"))
            return ResponseEntity.ok(mapOf("token" to token))
        } else if (authRequest.username == "user" && authRequest.password == "password") {
            val token = jwtService.createToken(authRequest.username, listOf("ROLE_USER"))
            return ResponseEntity.ok(mapOf("token" to token))
        }else if (authRequest.username == "readonly" && authRequest.password == "password") {
            val token = jwtService.createToken(authRequest.username, listOf("ROLE_READ-ONLY"))
            return ResponseEntity.ok(mapOf("token" to token))
        }
        return ResponseEntity.status(401).body("Invalid credentials")
    }
}
