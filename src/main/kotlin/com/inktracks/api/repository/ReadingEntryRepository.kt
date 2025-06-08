package com.inktracks.api.repository

import com.inktracks.api.model.ReadingEntry
import org.springframework.data.jpa.repository.JpaRepository

interface ReadingEntryRepository : JpaRepository<ReadingEntry, Long> {
    fun findByUserIdOrderByDateDesc(userId: Long): List<ReadingEntry>

    fun findByBookIdOrderByDateDesc(bookId: String): List<ReadingEntry>

    fun findByUserIdAndBookId(userId: Long, bookId: String): List<ReadingEntry>
}
