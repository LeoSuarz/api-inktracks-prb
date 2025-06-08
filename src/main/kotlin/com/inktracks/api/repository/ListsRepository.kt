package com.inktracks.api.repository

import com.inktracks.api.model.Lists
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ListsRepository : JpaRepository<Lists, Long> {
    fun findByUserId(userId: Long): List<Lists>
}
