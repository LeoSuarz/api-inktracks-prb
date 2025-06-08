package com.inktracks.api.service

import com.inktracks.api.model.ReadingList
import com.inktracks.api.repository.ReadingListRepository
import org.springframework.stereotype.Service

@Service
class ReadingListService(private val readingListRepository: ReadingListRepository) {

    fun save(readingList: ReadingList): ReadingList = readingListRepository.save(readingList)

    fun findById(id: Long): ReadingList? = readingListRepository.findById(id).orElse(null)

    fun findByUser(userId: Long): List<ReadingList> = readingListRepository.findByUserId(userId)
}
