package com.inktracks.api.repository

import com.inktracks.api.model.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository : JpaRepository<Book, Long> {
    fun findByVolumeId(volumeId: String): Book?
    fun findByTitleContainingIgnoreCase(title: String): List<Book>
}
