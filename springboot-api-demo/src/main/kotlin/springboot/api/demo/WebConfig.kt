package springboot.api.demo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import springboot.api.demo.middleware.CorrelationIdInterceptor

@Configuration
class WebConfig : WebMvcConfigurer {

    @Autowired
    private lateinit var correlationIdInterceptor: CorrelationIdInterceptor

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(correlationIdInterceptor)
    }
}
