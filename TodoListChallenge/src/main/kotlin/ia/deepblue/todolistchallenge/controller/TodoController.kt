package ia.deepblue.todolistchallenge.controller

import ia.deepblue.todolistchallenge.domain.Todo
import ia.deepblue.todolistchallenge.domain.TodoDto
import ia.deepblue.todolistchallenge.service.TodoService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todo")
class TodoController(
    private val todoService: TodoService
) {
    @PostMapping("/create")
    fun create(@RequestBody todoDto: TodoDto): ResponseEntity<TodoDto>{
        return  ResponseEntity(todoService.create(todoDto), HttpStatus.CREATED)
    }

    @PostMapping("/update/{id}")
    fun update(
        @PathVariable id: String,
        @RequestBody todoDto: TodoDto): ResponseEntity<TodoDto>{
        return  ResponseEntity(todoService.update(id, todoDto), HttpStatus.CREATED)
    }

    @GetMapping("/list")
    fun list(): ResponseEntity<Page<TodoDto>>{
        return ResponseEntity.ok(todoService.list())
    }
    @DeleteMapping("delete/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Unit> {
        return ResponseEntity.ok(todoService.delete(id))
    }
}