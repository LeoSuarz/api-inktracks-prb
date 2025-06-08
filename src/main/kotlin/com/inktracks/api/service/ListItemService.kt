package com.inktracks.api.service

import com.inktracks.api.DTO.AddToListRequest
import com.inktracks.api.model.ListItem
import com.inktracks.api.repository.ListItemRepository
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class ListItemService(
    private val listItemRepo: ListItemRepository,
    private val bookService: BookService
) {
    fun addBookToList(listId: Long, dto: AddToListRequest) {
        // 1) Busca el Book por volumeId
        val book = bookService.findByVolumeId(dto.volumeId)
            ?: throw NotFoundException()

        // 2) Crea el ListItem y persístelo
        listItemRepo.save(
            ListItem(
                listId = listId,
                book = book
            )
        )
    }

    fun removeBookFromList(listId: Long, dto: AddToListRequest) {
        val book = bookService.findByVolumeId(dto.volumeId)
            ?: throw IllegalArgumentException("Libro no encontrado")

        val item = listItemRepo.findByListIdAndBookId(listId, book.id)
            ?: throw IllegalArgumentException("El libro no está en esta lista")

        listItemRepo.delete(item)
    }


    fun getItemsInList(listId: Long): List<ListItem> =
        listItemRepo.findByListId(listId)
}
