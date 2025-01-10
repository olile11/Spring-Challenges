package ia.deepblue.catalogservice

import ia.deepblue.catalogservice.domain.Book
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests{

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun contextLoads() {
    }

    @Test
    fun `perform post books add should create a new book`() {
        val book = Book(
            "1234567890",
            "The Winter is coming",
            "Marting R. R.", 29.90)
        webTestClient
            .post()
            .uri("/v0/books/add")
            .bodyValue(book)
            .exchange()
            .expectStatus().isCreated
            .expectBody(Book::class.java).value { actualBook ->
                assert(actualBook != null)
                assert(actualBook.isbn == book.isbn)
                assert(actualBook.title == book.title)
                assert(actualBook.author == book.author)
                assert(actualBook.price == book.price)
            }
    }
}
