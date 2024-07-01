package mk.ru.shop.handlers

import mk.ru.shop.exceptions.BaseException
import mk.ru.shop.utils.ErrorDetails
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {
    private val log: Logger = LoggerFactory.getLogger(this.javaClass.name)

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(e: BaseException): ResponseEntity<ErrorDetails> {
        log.error(e.message)
        return ResponseEntity.status(e.status).body(
            ErrorDetails(
                message = e.message,
                exceptionProducer = e.stackTrace[0].className.toString(),
                exceptionClass = e.javaClass.name
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorDetails> {
        val message: String = e.message ?: "Unexpected error"
        log.error(message)
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
            ErrorDetails(
                message = message,
                exceptionProducer = e.stackTrace[0].className.toString(),
                exceptionClass = e.javaClass.name
            )
        )
    }
}