package com.example.demo.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "users")
@Schema(description = "User entity representing a user in the system")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    val id: Long? = null,

    @Column(nullable = false)
    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    val name: String,

    @Column(nullable = false, unique = true)
    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    val email: String,

    @Column(name = "created_at", nullable = false, updatable = false)
    @Schema(description = "Timestamp when the user was created", example = "2025-10-22T21:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
