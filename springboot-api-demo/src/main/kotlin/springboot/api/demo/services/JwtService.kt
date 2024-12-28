package springboot.api.demo.services

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.JWSHeader
import com.nimbusds.jose.crypto.MACSigner
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import org.springframework.stereotype.Service
import java.util.*
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.security.PrivateKey
import java.security.spec.PKCS8EncodedKeySpec
import com.nimbusds.jose.crypto.RSASSASigner
import java.security.KeyFactory
import jakarta.annotation.PostConstruct
import java.io.InputStream
import springboot.api.demo.AppConfig

@Service
class JwtService(private val appConfig: AppConfig) {

    private lateinit var issuer: String
    private lateinit var privateKey: PrivateKey

    @PostConstruct
    fun init() {
        val keyStream = this::class.java.getResourceAsStream("/private_key_pkcs8.pem")
        issuer = appConfig.issuer
        println("KeyStream is null: ${keyStream == null}, issuer: $issuer,")       
        requireNotNull(keyStream) { "Private key file not found" }
        try {
            privateKey = getPrivateKeyFromPem(keyStream)
            println("Private key successfully loaded!")
        } catch (e: Exception) {
            println("Error parsing private key: ${e.message}")
            throw e
        }
    }

    fun createToken(username: String, roles: List<String>): String {
        val now = Instant.now()
        val expiry = now.plus(1, ChronoUnit.HOURS)// 1 hour
        val claims = JWTClaimsSet.Builder()
            .subject(username)
            .claim("roles", roles)
            .issuer(issuer)
            .audience(username)
            .issueTime(Date.from(now))
            .expirationTime(Date.from(expiry)) 
            .build()

        val signer = RSASSASigner(privateKey)
        val signedJWT = SignedJWT(
            JWSHeader.Builder(JWSAlgorithm.RS256).build(),
            claims
        )
        signedJWT.sign(signer)

        return signedJWT.serialize()
    }

    fun getPrivateKeyFromPem(inputStream: InputStream): PrivateKey {
        val keyBytes = inputStream.readBytes()
        val keyString = String(keyBytes)
            .replace("-----BEGIN PRIVATE KEY-----", "")
            .replace("-----END PRIVATE KEY-----", "")
            .replace("\\s".toRegex(), "")
        val decodedKey = Base64.getDecoder().decode(keyString)
        val keySpec = PKCS8EncodedKeySpec(decodedKey)
        val keyFactory = KeyFactory.getInstance("RSA")
        return keyFactory.generatePrivate(keySpec)
    }
}
