package com.inktracks.api.model

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "reading_status")
data class ReadingStatus(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "book_id", insertable = false, updatable = false)
    val bookId: Long,

    @ManyToOne
    val user: User,

    @ManyToOne
    val book: Book,

    val startDate: LocalDate,
    val endDate: LocalDate? = null,
    val status: String  // e.g., "Reading", "Completed", "Dropped"
)