package ia.deepblue.catalogservice.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.JsonTest
import org.springframework.boot.test.json.JacksonTester

@JsonTest
class BookJasonTests {
    @Autowired private lateinit
    var json: JacksonTester<Book>

    @Test
    fun testSerialize(){
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Marting R. R.",
            price = 29.90,
            createdDate = null,
            lastModifiedDate = null)
        val jsonContent = json.write(book)

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
            .isEqualTo("1234567890")
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
            .isEqualTo("The Winter is coming")
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
            .isEqualTo("Marting R. R.")
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price")
            .isEqualTo(29.90)
    }

    @Test
    fun testDeserialize(){
        val content = """
            {
                "isbn": "1234567890",
                "title": "The Winter is coming",
                "author": "Marting R. R.",
                "price": 29.90
            }
        """.trimIndent()
        val book = Book(
            isbn = "1234567890",
            title = "The Winter is coming",
            author = "Marting R. R.",
            price = 29.90,
            createdDate = null,
            lastModifiedDate = null)
        assertThat(json.parse(content)).isEqualTo(book)
    }
}