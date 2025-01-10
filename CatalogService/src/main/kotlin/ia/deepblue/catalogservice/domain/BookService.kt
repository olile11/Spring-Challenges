package ia.deepblue.catalogservice.domain

import org.springframework.stereotype.Service
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun listBook(): List<Book> = bookRepository.findAll()
    fun bookDetails(isbn: String): Book {
        println("isbn: $isbn")
        return bookRepository.findByIsbn(isbn)
            .orElseThrow { BookNotFoundException(isbn) }
    }
    fun addBook(book: Book): Book? {
        when(bookRepository.existsByIsbn(book.isbn)) {
            true -> throw BookAlreadyExistsException(book.isbn)
            else -> return bookRepository.save(book)
        }
    }
    fun removeBook(isbn: String) = bookRepository.deleteByIsbn(isbn)
    fun updateBook(isbn: String, book: Book) {
        bookRepository.findByIsbn(isbn).map {
            it.copy(
                title = book.title,
                author = book.author,
                price = book.price
            )
            bookRepository.save(it)
        }.orElseThrow {
            IllegalArgumentException("Book with ISBN $isbn not found")
        }
    }
}