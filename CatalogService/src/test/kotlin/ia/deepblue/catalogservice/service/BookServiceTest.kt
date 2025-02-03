package ia.deepblue.catalogservice.service

import ia.deepblue.catalogservice.domain.*
import ia.deepblue.catalogservice.exception.BookAlreadyExistsException
import ia.deepblue.catalogservice.exception.BookNotFoundException
import ia.deepblue.catalogservice.repository.BookRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import java.util.*

@ExtendWith(MockitoExtension::class)
class BookServiceTest{
    @Mock lateinit var
    bookRepository: BookRepository

    @InjectMocks lateinit var
    bookService: BookService

    @Test
    fun `when create book but already exist`(){
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Marting R. R.", price = 29.90,
            createdDate = null, lastModifiedDate = null)
        `when`(bookRepository.existsByIsbn(book.isbn))
            .thenReturn(true)
        assertThatThrownBy { bookService.addBook(book) }
            .isInstanceOf(BookAlreadyExistsException::class.java)
            .hasMessage("The book with ISBN ${book.isbn} already exists.")
    }

    @Test
    fun `when book not exist`(){
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Marting R. R.", price = 29.90,
            createdDate = null, lastModifiedDate = null)
        `when`(bookRepository.findByIsbn(book.isbn))
            .thenReturn(Optional.empty())
        assertThatThrownBy { bookService.bookDetails(book.isbn) }
            .isInstanceOf(BookNotFoundException::class.java)
            .hasMessage("The book with ISBN ${book.isbn} was not found.")
    }

}