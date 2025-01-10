package ia.deepblue.catalogservice.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BookAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    fun handleAlreadyExist(ex: BookAlreadyExistsException): String {
        return "A book with ISBN ${ex.message} already exists."
    }

    @ExceptionHandler(BookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(ex: BookNotFoundException): String {
        return "The book with ISBN ${ex.message} was not found."
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

class BookNotFoundException(message: String) : RuntimeException(message)
class BookAlreadyExistsException(message: String) : RuntimeException(message)
