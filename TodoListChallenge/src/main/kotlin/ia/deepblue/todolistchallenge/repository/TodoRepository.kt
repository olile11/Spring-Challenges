package ia.deepblue.todolistchallenge.repository

import ia.deepblue.todolistchallenge.domain.Todo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TodoRepository: JpaRepository<Todo, String> {
}