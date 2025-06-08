package com.inktracks.api.dto

import com.inktracks.api.model.ReadingEntry
import java.time.format.DateTimeFormatter

data class ReadingEntryResponse(
    val id: Long,
    val userId: Long,
    val bookId: String,
    val rating: Int?,
    val comment: String?,
    val startDate: String,  // Campo que espera el frontend
    val endDate: String = ""  // Campo vacío por ahora
) {
    companion object {
        fun fromEntity(entity: ReadingEntry): ReadingEntryResponse {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            return ReadingEntryResponse(
                id = entity.id,
                userId = entity.userId,
                bookId = entity.bookId,
                rating = entity.rating,
                comment = entity.comment,
                startDate = entity.date.format(formatter),
                endDate = ""
            )
        }
    }
}
