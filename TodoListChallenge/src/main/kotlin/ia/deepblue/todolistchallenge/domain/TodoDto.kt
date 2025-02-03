package ia.deepblue.todolistchallenge.domain

class TodoDto(
    var name: String,
    var description: String,
    val isDid: Boolean = false,
    val priority: Int = 0
){
    companion object{
        fun toEntity(todoDto: TodoDto): Todo{
            return Todo(
                isDid = todoDto.isDid,
                name = todoDto.name,
                priority = todoDto.priority,
                description = todoDto.description)
        }
        fun toDto(todo: Todo): TodoDto{
            return TodoDto(
                isDid = todo.isDid,
                name = todo.name,
                priority = todo.priority,
                description = todo.description)
        }
    }
}