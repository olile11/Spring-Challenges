package ia.deepblue.catalogservice

import ia.deepblue.catalogservice.controller.BookController
import ia.deepblue.catalogservice.domain.Book
import ia.deepblue.catalogservice.domain.BookNotFoundException
import ia.deepblue.catalogservice.domain.BookService
import jakarta.validation.Validation
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(BookController::class)
class BookControllerMvcTest(
    @Autowired val mockMvc: MockMvc
){
    @MockitoBean
    private lateinit var bookService: BookService

    @Test
    fun `when get book not existing should return 404`() {
        val isbn = "a123456789"
        given(bookService.bookDetails(isbn))
            .willThrow(BookNotFoundException::class.java)

        mockMvc
            .perform(get("/v0/books/retrieve/$isbn"))
            .andExpect(status().isNotFound)
    }
}