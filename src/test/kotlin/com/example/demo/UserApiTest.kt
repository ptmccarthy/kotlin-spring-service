package com.example.demo

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@SpringBootTest
@AutoConfigureMockMvc
class UserApiTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
    }

    @Test
    fun `should create a new user`() {
        val user = mapOf(
            "name" to "John Doe",
            "email" to "john@example.com"
        )

        mockMvc.perform(
            post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"))
            .andExpect(jsonPath("$.createdAt").exists())
    }

    @Test
    fun `should get all users`() {
        // Create test users
        userRepository.save(User(name = "John Doe", email = "john@example.com"))
        userRepository.save(User(name = "Jane Smith", email = "jane@example.com"))

        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].name").value("John Doe"))
            .andExpect(jsonPath("$[1].name").value("Jane Smith"))
    }

    @Test
    fun `should get user by id`() {
        val savedUser = userRepository.save(User(name = "John Doe", email = "john@example.com"))

        mockMvc.perform(get("/api/users/${savedUser.id}"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(savedUser.id))
            .andExpect(jsonPath("$.name").value("John Doe"))
            .andExpect(jsonPath("$.email").value("john@example.com"))
    }

    @Test
    fun `should return 404 when user not found`() {
        mockMvc.perform(get("/api/users/999"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should update user`() {
        val savedUser = userRepository.save(User(name = "John Doe", email = "john@example.com"))

        val updatedUser = mapOf(
            "name" to "John Updated",
            "email" to "john.updated@example.com"
        )

        mockMvc.perform(
            put("/api/users/${savedUser.id}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedUser))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("John Updated"))
            .andExpect(jsonPath("$.email").value("john.updated@example.com"))
    }

    @Test
    fun `should return 404 when updating non-existent user`() {
        val user = mapOf(
            "name" to "John Doe",
            "email" to "john@example.com"
        )

        mockMvc.perform(
            put("/api/users/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user))
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `should delete user`() {
        val savedUser = userRepository.save(User(name = "John Doe", email = "john@example.com"))

        mockMvc.perform(delete("/api/users/${savedUser.id}"))
            .andExpect(status().isNoContent)

        // Verify user was deleted
        assert(!userRepository.existsById(savedUser.id!!))
    }

    @Test
    fun `should handle empty user list`() {
        mockMvc.perform(get("/api/users"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(0))
    }
}
