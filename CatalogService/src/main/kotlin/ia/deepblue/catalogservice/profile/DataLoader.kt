package ia.deepblue.catalogservice.profile

import ia.deepblue.catalogservice.domain.Book
import ia.deepblue.catalogservice.repository.BookRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.annotation.Profile
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
@Profile("testdata")
class DataLoader(
    private val bookRepository: BookRepository
) {
    @EventListener(ApplicationReadyEvent::class)
    fun loadBookTestData(){
        bookRepository.saveAll(
            listOf(
                Book(
                    id = null,
                    isbn = "1234567890",
                    title = "The Winter is coming",
                    author = "Marting R. R.", 29.90,
                    createdDate = null,
                    lastModifiedDate = null
                ),
                Book(
                    id = null,
                    isbn = "1234567891",
                    title = "The battle of the bastards",
                    author = "Marting R. R.", 30.90,
                    createdDate = null,
                    lastModifiedDate = null),
                Book(
                    id = null,
                    isbn = "1234567892",
                    title = "The winds of winter",
                    author = "Marting R. R.", 15.90,
                    createdDate = null,
                    lastModifiedDate = null),
            )
        )
    }
}