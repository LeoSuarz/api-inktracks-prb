package com.inktracks.api.repository

import com.inktracks.api.model.Favorite
import com.inktracks.api.model.FavoriteId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FavoriteRepository : JpaRepository<Favorite, FavoriteId> {
    fun findByUserId(userId: Long): List<Favorite>
}
