package com.inktracks.api.repository

import com.inktracks.api.model.ReadingStatus
import org.springframework.data.jpa.repository.JpaRepository


interface ReadingStatusRepository : JpaRepository<ReadingStatus, Long> {
    fun findByBookId(bookId: Long): List<ReadingStatus>
    fun findByUserId(userId: Long): List<ReadingStatus>
}
