package ia.deepblue.todolistchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoListChallengeApplication

fun main(args: Array<String>) {
    runApplication<TodoListChallengeApplication>(*args)
}
