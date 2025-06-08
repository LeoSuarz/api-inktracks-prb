package com.inktracks.api.repository

import com.inktracks.api.model.ReadingList
import org.springframework.data.jpa.repository.JpaRepository

interface ReadingListRepository : JpaRepository<ReadingList, Long> {
    fun findByUserId(userId: Long): List<ReadingList>
}
