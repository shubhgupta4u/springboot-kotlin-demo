package springboot.api.demo.util

import org.slf4j.MDC
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import springboot.api.demo.middleware.CorrelationIdInterceptor

@Component
class GlobalAccessDeniedHandler : AccessDeniedHandler {

    private val logger = LoggerFactory.getLogger(GlobalAccessDeniedHandler::class.java)

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, accessDeniedException: AccessDeniedException) {
        // Retrieve the correlation ID from MDC (if set)
        val correlationId = MDC.get(CorrelationIdInterceptor.CORRELATION_ID_HEADER_NAME) ?: "Unknown Correlation ID"

        // Log the access denied error with the correlation ID
        logger.error("Unauthorized access attempt with correlation ID: {}. Reason: {}", correlationId, accessDeniedException.message)

        // Return a 401 Unauthorized response with a message
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"

        val errorResponse = """
            {
                "message": "Unauthorized access. Please contact support with correlation ID: $correlationId"
            }
        """.trimIndent()

        response.writer.write(errorResponse)
    }
}
