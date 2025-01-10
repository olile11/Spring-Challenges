package ia.deepblue.catalogservice.domain

import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

interface BookRepository {
    fun findAll(): List<Book>
    fun findByIsbn(isbn: String): Optional<Book>
    fun existsByIsbn(isbn: String): Boolean
    fun save(book: Book): Book?
    fun deleteByIsbn(isbn: String)
}

@Repository
class InMemoryBookRepository: BookRepository{
    private val books: MutableMap<String, Book> = ConcurrentHashMap()
    override fun findAll(): List<Book> {
        return books.values.toList()
    }
    override fun findByIsbn(isbn: String): Optional<Book> {
        return if (existsByIsbn(isbn)) Optional.ofNullable(books[isbn])
            else Optional.empty()
    }
    override fun existsByIsbn(isbn: String): Boolean {
        return books[isbn] != null
    }
    override fun save(book: Book): Book? {
        books[book.isbn] = book
        return book
    }
    override fun deleteByIsbn(isbn: String) {
        books.remove(isbn)
    }
}