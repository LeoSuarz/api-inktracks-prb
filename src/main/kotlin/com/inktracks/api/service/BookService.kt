package com.inktracks.api.service

import com.inktracks.api.model.Book
import com.inktracks.api.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(private val repo: BookRepository) {
    fun saveOrGet(b: Book): Book =
        repo.findByVolumeId(b.volumeId)
            ?: repo.save(b)

    fun findById(id: Long): Book? = repo.findById(id).orElse(null)
    fun findByVolumeId(vol: String): Book? = repo.findByVolumeId(vol)
    fun findAll(): List<Book> = repo.findAll()
    fun searchByTitle(t: String): List<Book> = repo.findByTitleContainingIgnoreCase(t)
}