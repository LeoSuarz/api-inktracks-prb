package com.inktracks.api.repository

import com.inktracks.api.model.ListItem
import com.inktracks.api.model.ListItemId
import org.springframework.data.jpa.repository.JpaRepository

interface ListItemRepository : JpaRepository<ListItem, Long> {
    fun findByListId(listId: Long): List<ListItem>
    fun findByListIdAndBookId(listId: Long, bookId: Long): ListItem?

}