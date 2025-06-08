package com.inktracks.api.controller

import com.inktracks.api.DTO.AddToListRequest
import com.inktracks.api.DTO.BookDto
import com.inktracks.api.DTO.ListItemResponse
import com.inktracks.api.model.ListItem
import com.inktracks.api.service.ListItemService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/lists")
@CrossOrigin(origins = ["*"])
class ListItemController(private val svc: ListItemService) {

    @PostMapping("/{listId}/items")
    fun addBook(
        @PathVariable listId: Long,
        @RequestBody dto: AddToListRequest
    ): ResponseEntity<Void> {
        svc.addBookToList(listId, dto)
        return ResponseEntity.noContent().build()
    }

    // GET para depurar
    @GetMapping("/{listId}/items")
    fun items(@PathVariable listId: Long): List<ListItemResponse> =
        svc.getItemsInList(listId).map { listItem ->
            ListItemResponse(
                listItemsId = listItem.id,
                listId = listItem.listId,
                book = BookDto.from(listItem.book)
            )
        }

    @DeleteMapping("/{listId}/items")
    fun removeBookFromList(
        @PathVariable listId: Long,
        @RequestBody dto: AddToListRequest
    ): ResponseEntity<Void> {
        svc.removeBookFromList(listId, dto)
        return ResponseEntity.noContent().build()
    }
}
