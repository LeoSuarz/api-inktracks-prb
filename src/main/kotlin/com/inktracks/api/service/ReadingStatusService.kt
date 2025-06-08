package com.inktracks.api.service

import com.inktracks.api.model.ReadingStatus
import com.inktracks.api.repository.ReadingStatusRepository
import org.springframework.stereotype.Service

@Service
class ReadingStatusService(private val readingStatusRepository: ReadingStatusRepository) {

    fun save(status: ReadingStatus): ReadingStatus = readingStatusRepository.save(status)

    fun findByUser(userId: Long): List<ReadingStatus> = readingStatusRepository.findByUserId(userId)

    fun findByBook(bookId: Long): List<ReadingStatus> = readingStatusRepository.findByBookId(bookId)

}
