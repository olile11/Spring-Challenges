package ia.deepblue.todolistchallenge

import com.fasterxml.jackson.databind.ObjectMapper
import ia.deepblue.todolistchallenge.controller.TodoController
import ia.deepblue.todolistchallenge.domain.TodoDto
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class TodoListChallengeApplicationTests @Autowired constructor(
    var mockMvc: MockMvc,
    var objectMapper: ObjectMapper,
) {

    @Nested
    @DisplayName("handling todo class error!")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class Create {
        private val todoDto = TodoDto(
            "Lista de Matemática!",
            "Lista para")
        @Test
        fun `should create a new todo item`() {
            mockMvc.perform(
                post("/todo/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isCreated)
                .andExpect(jsonPath("$.name").value("Lista de Matemática!"))
                .andExpect(jsonPath("$.description").value("Lista para"))
        }

        @Test
        fun `bad request on creation`() {
            mockMvc.perform(
                post("/todo/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(todoDto)))
                .andExpect(status().isBadRequest)
                .andExpect(jsonPath("$.name").value("Lista de Matemática!"))
                .andExpect(jsonPath("$.description").value("Lista para"))
        }

        @Test
        fun `should return every items`() {
            mockMvc.perform(
                get("/todo/list")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.totalElements").value(0))
        }
    }
}
