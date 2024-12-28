package springboot.api.demo.util

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import jakarta.annotation.PostConstruct
import java.security.PublicKey
import java.io.InputStream
import java.util.*
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.security.interfaces.RSAPublicKey
import com.nimbusds.jose.JWSAlgorithm
import org.springframework.security.oauth2.core.*
import springboot.api.demo.AppConfig

@Configuration
class JwtDecoderConfig(private val appConfig: AppConfig) {
    private lateinit var publicKey: RSAPublicKey
    private lateinit var expectedissuer : String
    private lateinit var expectedAudience :List<String>

    @PostConstruct
    fun init() {
        val keyStream = this::class.java.getResourceAsStream("/public-key.pem")
        expectedissuer = appConfig.issuer        
        expectedAudience =appConfig.audience.split(",").map { it.trim() }
        println("KeyStream is null: ${keyStream == null}, expectedissuer: $expectedissuer, expectedAudience: $expectedAudience")
        requireNotNull(keyStream) { "Public key file not found" }
        try {
            publicKey = getPublicKeyFromPem(keyStream)
            println("Public key successfully loaded!")
        } catch (e: Exception) {
            println("Error parsing public key: ${e.message}")
            throw e
        }
    }

    fun getPublicKeyFromPem(inputStream: InputStream): RSAPublicKey {
        val keyBytes = inputStream.readBytes()
        val keyString = String(keyBytes)
            .replace("-----BEGIN PUBLIC KEY-----", "")
            .replace("-----END PUBLIC KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decodedKey = Base64.getDecoder().decode(keyString)
        val keySpec = X509EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    }

    @Bean
    fun jwtParser(): JwtDecoder  {
        val decoder = NimbusJwtDecoder
                .withPublicKey(publicKey).build()
                decoder.setJwtValidator { jwt ->
                    val claims = jwt.claims
                    val issuer = claims["iss"] as? String
                    val audience = claims["aud"] as? List<String>
                    println("Issuer: $issuer, Audience: $audience, claims: $claims")
                    if (issuer != expectedissuer || audience ==null || !expectedAudience.any { it in audience })
                    {
                        OAuth2TokenValidatorResult.failure(OAuth2Error("invalid_token", "Invalid issuer or audience", null))
                    } else {
                        OAuth2TokenValidatorResult.success()
                    }
                }
                return decoder
    }
}