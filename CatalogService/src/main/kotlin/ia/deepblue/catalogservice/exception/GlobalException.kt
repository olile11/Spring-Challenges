package ia.deepblue.catalogservice.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleAlreadyExist(ex: BookAlreadyExistsException): String? {
        return ex.message
    }

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: BookNotFoundException): String? {
        return ex.message
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): Map<String, String> {
        val errors: MutableMap<String, String> = mutableMapOf()
        ex.bindingResult.fieldErrors.forEach{error ->
            error.defaultMessage?.let { errors[error.field] = it }
        }
        return errors
    }
}

class BookNotFoundException(message: String) : RuntimeException("The book with ISBN $message was not found.")
class BookAlreadyExistsException(message: String) : RuntimeException("The book with ISBN $message already exists.")
