package springboot.api.demo.util

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter

class CustomJwtAuthenticationConverter : JwtAuthenticationConverter() {
    fun extractAuthorities(jwt: Jwt): Collection<GrantedAuthority> {
        val roles = jwt.claims["roles"] as? List<*> ?: emptyList<Any>()
        return roles.mapNotNull { it.toString().let(::SimpleGrantedAuthority) }
    }
}
