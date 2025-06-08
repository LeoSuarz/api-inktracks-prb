package com.inktracks.api.service

import com.inktracks.api.model.ListItem
import com.inktracks.api.model.ListItemId
import com.inktracks.api.model.Lists
import com.inktracks.api.model.ReadingList
import com.inktracks.api.repository.BookRepository
import com.inktracks.api.repository.ListItemRepository
import com.inktracks.api.repository.ListsRepository
import com.inktracks.api.repository.ReadingListRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service


@Service
class ListsService(
    private val repo: ListsRepository,
    private val listItemRepository: ListItemRepository,
    private val bookRepository: BookRepository
) {
    fun findAll(): List<Lists> =
        repo.findAll()

    fun findById(id: Long): Lists? =
        repo.findById(id).orElse(null)

    fun findByUserId(userId: Long): List<Lists> =
        repo.findByUserId(userId)

    fun create(list: Lists): Lists =
        repo.save(list)

    fun update(id: Long, updated: Lists): Lists {
        val existing = repo.findById(id)
            .orElseThrow { EntityNotFoundException("List with id $id not found") }
        val toSave = existing.copy(
            name        = updated.name,
            description = updated.description ?: existing.description
            // userId y createdAt no se cambian
        )
        return repo.save(toSave)
    }


    fun delete(id: Long): Boolean {
        return if (repo.existsById(id)) {
            repo.deleteById(id)
            true
        } else {
            false
        }
    }
}
