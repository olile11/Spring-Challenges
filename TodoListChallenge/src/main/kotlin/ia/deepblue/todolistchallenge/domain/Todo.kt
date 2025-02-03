package ia.deepblue.todolistchallenge.domain

import ch.qos.logback.core.testUtil.RandomUtil
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity
class Todo(
    var name: String,
    var description: String,
    val isDid: Boolean = false,
    val priority: Int = 0,
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID? = null
)