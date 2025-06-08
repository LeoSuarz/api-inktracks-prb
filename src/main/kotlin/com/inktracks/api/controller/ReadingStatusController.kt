package com.inktracks.api.controller

import com.inktracks.api.model.ReadingStatus
import com.inktracks.api.service.ReadingStatusService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reading-status")
class ReadingStatusController(private val readingStatusService: ReadingStatusService) {

    @PostMapping
    fun save(@RequestBody readingStatus: ReadingStatus): ReadingStatus =
        readingStatusService.save(readingStatus)

    @GetMapping("/user/{userId}")
    fun findByUser(@PathVariable userId: Long): List<ReadingStatus> =
        readingStatusService.findByUser(userId)

    @GetMapping("/book/{bookId}")
    fun findByBook(@PathVariable bookId: Long): List<ReadingStatus> =
        readingStatusService.findByBook(bookId)
}
