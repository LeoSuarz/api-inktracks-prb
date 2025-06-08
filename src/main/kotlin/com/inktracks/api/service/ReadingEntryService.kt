package com.inktracks.api.service

import com.inktracks.api.model.ReadingEntry
import com.inktracks.api.repository.ReadingEntryRepository
import org.springframework.stereotype.Service

@Service
class ReadingEntryService(private val repo: ReadingEntryRepository) {
    fun save(entry: ReadingEntry): ReadingEntry = repo.save(entry)

    fun findByUser(userId: Long): List<ReadingEntry> =
        repo.findByUserIdOrderByDateDesc(userId)

    fun findByBook(bookId: String): List<ReadingEntry> =
        repo.findByBookIdOrderByDateDesc(bookId)

    fun findAll(): List<ReadingEntry> = repo.findAll()
}