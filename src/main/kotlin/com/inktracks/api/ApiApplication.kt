package com.inktracks.api

import jakarta.annotation.PostConstruct
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

import org.slf4j.LoggerFactory

@SpringBootApplication
class ApiApplication


fun main(args: Array<String>) {
    val logger = LoggerFactory.getLogger(ApiApplication::class.java)
    logger.info("Starting InkTracks API...")
    runApplication<ApiApplication>(*args)

}