package springboot.api.demo

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("app")
class AppConfig {
    lateinit var issuer: String
    lateinit var audience:String
}
