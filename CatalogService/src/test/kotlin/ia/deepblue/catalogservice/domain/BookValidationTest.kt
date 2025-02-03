package ia.deepblue.catalogservice.domain

import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
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
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Marting R. R.", price = 29.90,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assert(violations.isEmpty())
    }

    @Test
    fun `isbn defined but incorrect validation should fail`() {
        val book = Book(
            isbn = "a234567890",
            title = "The Winter is coming",
            author = "Author", price = 9.90,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message)
            .isEqualTo("The ISBN format must be valid.")
    }

    @Test
    fun `isbn not defined validation should fail`() {
        val book = Book(
            isbn = "",
            title = "The Winter is coming",
            author = "Author", price = 9.90,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        val violationMgs = violations.map { it.message }
        assertThat(violations.size == 2)
        assertThat(violationMgs)
            .contains("The book ISBN must be defined.")
            .contains("The ISBN format must be valid.")
    }

    @Test
    fun `if title is empty validation should fail`() {
        val book = Book(
            isbn = "1234567890",
            title = "",
            author = "Marting R. R.", price = 29.90,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message)
            .isEqualTo("The book title must be defined.")
    }

    @Test
    fun `if author is empty validation should fail`() {
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "", price =  29.90,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message)
            .isEqualTo("The book author must be defined.")
    }

    @Test
    fun `if price is less or equal to zero validation should fail`() {
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Martin R. R.", price =  -10.0,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message)
            .isEqualTo("The book price must be greater than zero.")
    }

    @Test
    fun `if price null validation should fail`() {
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Martin R. R.", price = null,
            createdDate = null, lastModifiedDate = null)
        val violations: Set<ConstraintViolation<Book>> = validator.validate(book)
        assertThat(violations).hasSize(1)
        assertThat(violations.first().message)
            .isEqualTo("The book price must be defined.")
    }
}