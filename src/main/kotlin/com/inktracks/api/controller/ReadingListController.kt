package com.inktracks.api.controller

import com.inktracks.api.model.ReadingList
import com.inktracks.api.service.ReadingListService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reading-lists")
class ReadingListController(private val readingListService: ReadingListService) {

    @PostMapping
    fun save(@RequestBody readingList: ReadingList): ReadingList =
        readingListService.save(readingList)

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ReadingList? =
        readingListService.findById(id)

    @GetMapping("/user/{userId}")
    fun findByUser(@PathVariable userId: Long): List<ReadingList> =
        readingListService.findByUser(userId)
}
