package com.inktracks.api.controller

import com.inktracks.api.DTO.CreateBookRequest
import com.inktracks.api.DTO.BookDto
import com.inktracks.api.model.Book
import com.inktracks.api.service.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException



@RestController
@RequestMapping("/api/books")
class BookController(private val svc: BookService) {

    @PostMapping
    fun create(@RequestBody req: CreateBookRequest): ResponseEntity<BookDto> {
        val book = svc.saveOrGet(
            Book(
                volumeId = req.volumeId,
                title    = req.title,
                author   = req.author
            )
        )
        val dto = BookDto.from(book)
        return if (book.id == 0L)
            ResponseEntity.status(HttpStatus.CREATED).body(dto)
        else
            ResponseEntity.ok(dto)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long) =
        svc.findById(id)?.let(BookDto::from)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @GetMapping("/by-volume/{vid}")
    fun getByVolume(@PathVariable vid: String) =
        svc.findByVolumeId(vid)?.let(BookDto::from)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    @GetMapping
    fun all() = svc.findAll().map(BookDto::from)

    @GetMapping("/search")
    fun search(@RequestParam title: String) =
        svc.searchByTitle(title).map(BookDto::from)
}