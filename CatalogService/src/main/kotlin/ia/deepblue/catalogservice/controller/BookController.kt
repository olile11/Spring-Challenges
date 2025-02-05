package ia.deepblue.catalogservice.controller

import ia.deepblue.catalogservice.configs.PolarProperties
import ia.deepblue.catalogservice.domain.Book
import ia.deepblue.catalogservice.service.BookService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v0/books")
class BookController @Autowired constructor (
    private val bookService: BookService,
    private val polarProperties: PolarProperties
) {
//    @GetMapping
//    fun greeting(): String {
//        return polarProperties.greeting
//    }

    @GetMapping("/retrieve")
    @ResponseStatus(HttpStatus.OK)
    fun getBooks(): MutableIterable<Book> {
        return bookService.listBook()
    }

    @GetMapping("/retrieve/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    fun getByIsbn(@PathVariable isbn: String): Book{
        return bookService.bookDetails(isbn)
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    fun add(@Valid @RequestBody book: Book): Book? {
        return bookService.addBook(book)
    }

    @DeleteMapping("/delete/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun remove(@PathVariable isbn: String) {
        bookService.removeBook(isbn)
    }

    @PutMapping("/update/{isbn}")
    @ResponseStatus(HttpStatus.OK)
    fun update(
        @Valid
        @PathVariable isbn: String,
        @RequestBody book: Book): Book? {
        return bookService.updateBook(isbn, book)
    }
}