package springboot.api.demo.util

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.slf4j.MDC
import springboot.api.demo.middleware.CorrelationIdInterceptor
import springboot.api.demo.exception.UnauthorizedException
import org.slf4j.LoggerFactory
import org.springframework.security.authorization.AuthorizationDeniedException

@ControllerAdvice
class GlobalExceptionHandler {

    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
    
    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, String?>> {
        val errors = ex.bindingResult.fieldErrors.associate { it.field to it.defaultMessage }
        return ResponseEntity(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleUnhandledExceptions(ex: Exception): ResponseEntity<Map<String, String?>> {
        val correlationId = MDC.get(CorrelationIdInterceptor.CORRELATION_ID_HEADER_NAME)
        logger.error("Error occurred with correlation ID: {} -> {}", correlationId, ex.message)
        logger.error("Error occurred with correlation ID: {}", correlationId, ex)
        val errorResponse = mapOf(
            "message" to "An unexpected error occurred. Please contact support with correlation ID: $correlationId"
        )
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
    
    @ExceptionHandler(AuthorizationDeniedException::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    fun handleAccessDeniedException(ex: AuthorizationDeniedException): ResponseEntity<Map<String, String?>> {
        val correlationId = MDC.get(CorrelationIdInterceptor.CORRELATION_ID_HEADER_NAME)
        println("Access denied. Correlation ID: $correlationId ->${ex.message}")
        logger.error("Access denied. Correlation ID: $correlationId", ex)

        return ResponseEntity(
            mapOf("message" to "Access Denied. Please contact support with correlation ID: $correlationId"),
            HttpStatus.FORBIDDEN
        )
    }

    // @ExceptionHandler(AccessDeniedException::class)
    // @ResponseStatus(HttpStatus.UNAUTHORIZED)
    // fun handleUnauthorizedExceptions(ex: AccessDeniedException): ResponseEntity<Map<String, String?>> {
    //     val correlationId = MDC.get(CorrelationIdInterceptor.CORRELATION_ID_HEADER_NAME)
    //     logger.error("Unauthorized access with correlation ID: {}", correlationId, ex)
    //     val errorResponse = mapOf(
    //         "message" to "Unauthorized access. Please contact support with correlation ID: $correlationId"
    //     )
    //     return ResponseEntity(errorResponse, HttpStatus.UNAUTHORIZED)
    // }

    @ExceptionHandler(AccessDeniedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<Map<String, String?>> {
        val correlationId = MDC.get(CorrelationIdInterceptor.CORRELATION_ID_HEADER_NAME)
        logger.error("Access denied with correlation ID: {}", correlationId, ex)
        val errorResponse = mapOf(
            "message" to "Access denied. Please contact support with correlation ID: $correlationId"
        )
        return ResponseEntity(errorResponse, HttpStatus.FORBIDDEN)
    }
}