package ia.deepblue.catalogservice.data

import ia.deepblue.catalogservice.configs.DataConfig
import ia.deepblue.catalogservice.domain.Book
import ia.deepblue.catalogservice.repository.BookRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Import
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.test.context.ActiveProfiles
import java.util.*

@DataJdbcTest
@Import(DataConfig::class)
@AutoConfigureTestDatabase(
    replace = AutoConfigureTestDatabase.Replace.NONE
)
@ActiveProfiles("integration")
class BookRepositoryJdbcTests @Autowired constructor(
    private val bookRepository: BookRepository,
    private val jdbcAggregateTemplate: JdbcAggregateTemplate
) {
    @Test
    fun findBookByIsbnWhenExisting() {
        val bookIsbn = "1234561237"
        val book = Book(
            isbn = bookIsbn,
            title = "Title",
            author = "Author", price = 12.90,
            createdDate = null, lastModifiedDate = null
        )
        jdbcAggregateTemplate.insert(book)
        val actualBook = bookRepository.findByIsbn(bookIsbn)

        assertThat(actualBook).isPresent
        assertThat(actualBook.get().isbn).isEqualTo(book.isbn)
    }

    @Test
    fun findBookByIsbnWhenNotExisting() {
        val actualBook: Optional<Book> = bookRepository.findByIsbn("1234561238")
        assertThat(actualBook).isEmpty
    }
}