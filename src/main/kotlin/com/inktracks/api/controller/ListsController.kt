package com.inktracks.api.controller

import com.inktracks.api.model.Lists
import com.inktracks.api.service.ListsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lists")
class ListsController(
    private val service: ListsService
) {

    @GetMapping
    fun getAll(): ResponseEntity<List<Lists>> =
        ResponseEntity.ok(service.findAll())

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ResponseEntity<Lists> =
        service.findById(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()

    @GetMapping("/user/{userId}")
    fun getByUser(@PathVariable userId: Long): ResponseEntity<List<Lists>> =
        ResponseEntity.ok(service.findByUserId(userId))

    @PostMapping
    fun create(@RequestBody list: Lists): ResponseEntity<Lists> {
        val created = service.create(list)
        return ResponseEntity.status(HttpStatus.CREATED).body(created)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody list: Lists): ResponseEntity<Lists> =
        try {
            ResponseEntity.ok(service.update(id, list))
        } catch (ex: Exception) {
            ResponseEntity.notFound().build()
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Void> =
        if (service.delete(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
}
