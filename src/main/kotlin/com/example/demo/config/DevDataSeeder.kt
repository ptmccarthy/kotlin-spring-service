package com.example.demo.config

import com.example.demo.model.User
import com.example.demo.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.LocalDateTime

@Configuration
@Profile("dev")
class DevDataSeeder {

    private val logger = LoggerFactory.getLogger(DevDataSeeder::class.java)

    @Bean
    fun loadDevData(userRepository: UserRepository): CommandLineRunner {
        return CommandLineRunner {
            // Check if dev seed has already run by looking for a known test user
            if (userRepository.findByEmail("john.doe@test.com") != null) {
                logger.info("Development data already exists (found john.doe@test.com), skipping seed")
                return@CommandLineRunner
            }

            logger.info("Seeding development test data...")

            val testUsers = listOf(
                User(
                    name = "John Doe",
                    email = "john.doe@test.com",
                    createdAt = LocalDateTime.now().minusDays(30)
                ),
                User(
                    name = "Jane Smith",
                    email = "jane.smith@test.com",
                    createdAt = LocalDateTime.now().minusDays(25)
                ),
                User(
                    name = "Bob Johnson",
                    email = "bob.johnson@test.com",
                    createdAt = LocalDateTime.now().minusDays(20)
                ),
                User(
                    name = "Alice Williams",
                    email = "alice.williams@test.com",
                    createdAt = LocalDateTime.now().minusDays(15)
                ),
                User(
                    name = "Charlie Brown",
                    email = "charlie.brown@test.com",
                    createdAt = LocalDateTime.now().minusDays(10)
                ),
                User(
                    name = "Diana Prince",
                    email = "diana.prince@test.com",
                    createdAt = LocalDateTime.now().minusDays(5)
                ),
                User(
                    name = "Ethan Hunt",
                    email = "ethan.hunt@test.com",
                    createdAt = LocalDateTime.now().minusDays(3)
                ),
                User(
                    name = "Fiona Gallagher",
                    email = "fiona.gallagher@test.com",
                    createdAt = LocalDateTime.now().minusDays(2)
                ),
                User(
                    name = "George Miller",
                    email = "george.miller@test.com",
                    createdAt = LocalDateTime.now().minusDays(1)
                ),
                User(
                    name = "Hannah Montana",
                    email = "hannah.montana@test.com",
                    createdAt = LocalDateTime.now()
                )
            )

            testUsers.forEach { user ->
                // Only create if email doesn't already exist
                if (userRepository.findByEmail(user.email) == null) {
                    userRepository.save(user)
                    logger.info("Created test user: ${user.name} (${user.email})")
                }
            }

            logger.info("Development data seeding completed. Total users: ${userRepository.count()}")
        }
    }
}
