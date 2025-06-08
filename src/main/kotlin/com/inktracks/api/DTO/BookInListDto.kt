package com.inktracks.api.DTO

data class BookInListDto(
    val id: String,
    val title: String,
    val author: String?,
    val coverUrl: String?
)
