package com.inktracks.api.DTO

import com.inktracks.api.model.Book

data class BookDto(
    val id: Long,
    val volumeId: String,
    val title: String,
    val author: String
) {
    companion object {
        fun from(b: Book) = BookDto(
            id       = b.id,
            volumeId = b.volumeId,
            title    = b.title,
            author   = b.author
        )
    }
}
