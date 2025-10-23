package com.example.demo.controller

import com.example.demo.model.User
import com.example.demo.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Users", description = "User management API")
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    @Operation(summary = "Get all users", description = "Returns a list of all users in the system")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    fun getAllUsers(): List<User> = userService.getAllUsers()

    @Operation(summary = "Get user by ID", description = "Returns a single user by their ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User found",
            content = [Content(schema = Schema(implementation = User::class))]),
        ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
    ])
    @GetMapping("/{id}")
    fun getUserById(
        @Parameter(description = "ID of user to retrieve", required = true)
        @PathVariable id: Long
    ): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Create a new user", description = "Creates a new user and returns the created user")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "User created successfully",
            content = [Content(schema = Schema(implementation = User::class))]),
        ApiResponse(responseCode = "400", description = "Invalid input", content = [Content()])
    ])
    @PostMapping
    fun createUser(
        @Parameter(description = "User object to create", required = true)
        @RequestBody user: User
    ): ResponseEntity<User> {
        val createdUser = userService.createUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }

    @Operation(summary = "Update a user", description = "Updates an existing user by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "User updated successfully",
            content = [Content(schema = Schema(implementation = User::class))]),
        ApiResponse(responseCode = "404", description = "User not found", content = [Content()])
    ])
    @PutMapping("/{id}")
    fun updateUser(
        @Parameter(description = "ID of user to update", required = true)
        @PathVariable id: Long,
        @Parameter(description = "Updated user object", required = true)
        @RequestBody user: User
    ): ResponseEntity<User> {
        val updatedUser = userService.updateUser(id, user)
        return if (updatedUser != null) {
            ResponseEntity.ok(updatedUser)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Delete a user", description = "Deletes a user by ID")
    @ApiResponses(value = [
        ApiResponse(responseCode = "204", description = "User deleted successfully"),
        ApiResponse(responseCode = "404", description = "User not found")
    ])
    @DeleteMapping("/{id}")
    fun deleteUser(
        @Parameter(description = "ID of user to delete", required = true)
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        userService.deleteUser(id)
        return ResponseEntity.noContent().build()
    }
}
