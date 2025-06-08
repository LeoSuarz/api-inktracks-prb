package com.inktracks.api.service

import com.inktracks.api.model.Book
import com.inktracks.api.model.Favorite
import com.inktracks.api.model.FavoriteId
import com.inktracks.api.model.User
import com.inktracks.api.repository.BookRepository
import com.inktracks.api.repository.FavoriteRepository
import com.inktracks.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class FavoriteService(private val repo: FavoriteRepository,
                      private val userRepo: UserRepository,
                      private val bookRepo: BookRepository
) {

    fun addFavorite(userId: Long, bookId: Long) {
        val user = userRepo.findById(userId)
            .orElseThrow { RuntimeException("Usuario $userId no existe") }

        val book = bookRepo.findById(bookId)
            .orElseThrow { RuntimeException("Libro $bookId no existe") }

        val favId = FavoriteId(userId, bookId)
        if (!repo.existsById(favId)) {
            repo.save(Favorite(id = favId, user = user, book = book))
        }
    }

    fun listFavorites(userId: Long): List<Book> =
        repo.findByUserId(userId).map { it.book }

    fun removeFavorite(userId: Long, bookId: Long) {
        repo.deleteById(FavoriteId(userId, bookId))
    }
}
