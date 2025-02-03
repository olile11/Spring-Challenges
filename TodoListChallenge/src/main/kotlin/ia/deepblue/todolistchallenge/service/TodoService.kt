package ia.deepblue.todolistchallenge.service

import ia.deepblue.todolistchallenge.domain.Todo
import ia.deepblue.todolistchallenge.domain.TodoDto
import ia.deepblue.todolistchallenge.repository.TodoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.*

@Service
class TodoService(
    private val todoRepository: TodoRepository
) {
    fun create(todoDto: TodoDto): TodoDto = TodoDto.toDto(todoRepository.save(TodoDto.toEntity(todoDto)))
    fun findById(id: String): TodoDto = TodoDto.toDto(todoRepository.findById(id).get())
    fun update(id: String, todoDto: TodoDto): TodoDto {
        return findById(id).let{ actual ->
            with(actual) {
                todoDto.name.takeIf { it.isNotBlank() }?.let { name = it }
                todoDto.description.takeIf { it.isNotBlank() }?.let { description = it }
            }
            TodoDto.toDto(todoRepository.save(TodoDto.toEntity(actual)))
        }
    }
    fun list(): Page<TodoDto> {
        return todoRepository
            .findAll(PageRequest
                .of(0,
                    10,
                    Sort.by("priority")
                        .descending()
                        .and(Sort.by("name"))))
            .map { TodoDto.toDto(it) }
    }
    fun delete(id: String) = todoRepository.deleteById(id)
}