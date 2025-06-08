package com.inktracks.api.DTO

data class ListItemResponse(
    val listItemsId: Long,
    val listId: Long,
    val book: BookDto
)
