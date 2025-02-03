package ia.deepblue.catalogservice

import ia.deepblue.catalogservice.domain.Book
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests{

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun contextLoads() {
    }

    @Test
    fun `perform post books add should create a new book`() {
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Marting R. R.",
            price = 29.90,
            createdDate = null,
            lastModifiedDate = null)
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
