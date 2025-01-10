package ia.deepblue.catalogservice

import ia.deepblue.catalogservice.domain.Book
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BookValidationTest {
    private lateinit var validator: Validator

    @BeforeAll
    fun setup() {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun `should validate book with all fields`() {
        val book = Book(
            "1234567890",
            "The Winter is coming",
            "Marting R. R.", 29.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assert(violations.isEmpty())
    }

    @Test
    fun `isbn defined but incorrect validation should fail`() {
        val book = Book("a234567890", "The Winter is coming", "Author", 9.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assert(violations.size == 1)
        assert(violations.first().message.equals("The ISBN format must be valid."))
    }

    @Test
    fun `if title is empty validation should fail`() {
        val book = Book("1234567890", "", "Marting R. R.", 29.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assert(violations.size == 1)
        assert(violations.first().message.equals("The book title must be defined."))
    }

    @Test
    fun `if author is empty validation should fail`() {
        val book = Book("1234567890", "The Winter is coming", "", 29.90)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assert(violations.size == 1)
        assert(violations.first().message.equals("The book author must be defined."))
    }

    @Test
    fun `if price of book is less than or equal to zero validation should fail`() {
        val book = Book("1234567890", "The Winter is coming", "Martin R. R.", 0.0)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assert(violations.size == 1)
        assert(violations.first().message.equals("The book price must be greater than zero."))
    }
}