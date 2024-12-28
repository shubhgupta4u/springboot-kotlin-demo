package springboot.api.demo.middleware

import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import java.util.UUID

@Component
class CorrelationIdInterceptor : HandlerInterceptor {

    companion object {
        const val CORRELATION_ID_HEADER_NAME = "X-Correlation-Id"
    }

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var correlationId = request.getHeader(CORRELATION_ID_HEADER_NAME)
        if (correlationId == null || correlationId.isEmpty()) {
            correlationId = UUID.randomUUID().toString()
        }
        MDC.put(CORRELATION_ID_HEADER_NAME, correlationId)
        response.setHeader(CORRELATION_ID_HEADER_NAME, correlationId)
        return true
    }

    override fun afterCompletion(request: HttpServletRequest, response: HttpServletResponse, handler: Any, ex: Exception?) {
        MDC.remove(CORRELATION_ID_HEADER_NAME)
    }
}