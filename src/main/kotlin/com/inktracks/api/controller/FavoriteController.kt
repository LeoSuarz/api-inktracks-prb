package com.inktracks.api.controller

import com.inktracks.api.DTO.BookDto
import com.inktracks.api.service.FavoriteService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users/{userId}/favorites")
class FavoriteController(val svc: FavoriteService) {

    @GetMapping
    fun getFavorites(@PathVariable userId: Long): List<BookDto> =
        svc.listFavorites(userId).map { BookDto.from(it) }

    @PostMapping("/{bookId}")
    fun addFav(@PathVariable userId: Long, @PathVariable bookId: Long) {
        svc.addFavorite(userId, bookId)
    }

    @DeleteMapping("/{bookId}")
    fun removeFav(@PathVariable userId: Long, @PathVariable bookId: Long) {
        svc.removeFavorite(userId, bookId)
    }
}
