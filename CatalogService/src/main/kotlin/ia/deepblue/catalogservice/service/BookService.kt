package ia.deepblue.catalogservice.service

import ia.deepblue.catalogservice.domain.Book
import ia.deepblue.catalogservice.exception.BookAlreadyExistsException
import ia.deepblue.catalogservice.exception.BookNotFoundException
import ia.deepblue.catalogservice.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun listBook(): MutableIterable<Book> = bookRepository.findAll()
    fun bookDetails(isbn: String): Book {
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
    fun updateBook(isbn: String, book: Book): Book? {
        return bookRepository.findByIsbn(isbn).map {
            it.title = book.title
            it.author = book.author
            it.price = book.price
            it.version = book.version
            it.createdDate = book.createdDate
            it.lastModifiedDate = book.lastModifiedDate
            bookRepository.save(it)
        }.orElseThrow {
            IllegalArgumentException("Book with ISBN $isbn not found")
        }
    }
}