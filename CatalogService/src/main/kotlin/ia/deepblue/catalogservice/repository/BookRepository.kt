package ia.deepblue.catalogservice.repository

import ia.deepblue.catalogservice.domain.Book
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.transaction.annotation.Transactional
import java.util.Optional

interface BookRepository: CrudRepository<Book, String> {
    fun findByIsbn(isbn: String): Optional<Book>
    fun existsByIsbn(isbn: String): Boolean
    fun getAllById(id: Long): MutableList<Book>

    @Modifying
    @Transactional
    @Query("delete from book where isbn = :isbn")
    fun deleteByIsbn(isbn: String)
}
