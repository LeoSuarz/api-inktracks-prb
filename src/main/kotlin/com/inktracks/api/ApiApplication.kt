package com.inktracks.api

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Value
import org.slf4j.LoggerFactory

@SpringBootApplication
class ApiApplication

@RestController
class HealthController {

    private val logger = LoggerFactory.getLogger(HealthController::class.java)

    @Value("\${server.port}")
    private lateinit var port: String

    @PostConstruct
    fun init() {
        logger.info("=== APPLICATION STARTING ===")
        logger.info("Server port: $port")
        logger.info("Context path: /inktracks")
        logger.info("=== ENDPOINTS AVAILABLE ===")
        logger.info("GET /inktracks/ - Root endpoint")
        logger.info("GET /inktracks/health - Health check")
        logger.info("POST /inktracks/users - Register user")
        logger.info("POST /inktracks/users/login - Login user")
    }

    @GetMapping("/")
    fun root(): Map<String, Any> {
        logger.info("Root endpoint accessed")
        return mapOf(
            "message" to "InkTracks API is running!",
            "timestamp" to System.currentTimeMillis(),
            "port" to port,
            "status" to "OK"
        )
    }

    @GetMapping("/health")
    fun health(): Map<String, Any> {
        logger.info("Health check accessed")
        return mapOf(
            "status" to "UP",
            "timestamp" to System.currentTimeMillis(),
            "port" to port,
            "message" to "Application is healthy"
        )
    }
}

fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(ApiApplication::class.java)
    logger.info("Starting InkTracks API...")
    runApplication<ApiApplication>(*args)
}
