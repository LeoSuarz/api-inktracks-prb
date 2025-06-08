package com.inktracks.api.model

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "reading_entry")
data class ReadingEntry(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val userId: Long,
    val bookId: String,            // el ID viene de Google Books
    val rating: Int? = null,
    val comment: String? = null,

    // fecha/hora en que el usuario marcó la lectura
    val date: LocalDateTime = LocalDateTime.now()
)