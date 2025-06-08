package com.inktracks.api.model

import jakarta.persistence.*

@Entity
@Table(name = "books", uniqueConstraints = [UniqueConstraint(columnNames = ["volume_id"])])
data class Book(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "volume_id", nullable = false, length = 100)
    val volumeId: String,

    val title: String,
    val author: String,

    @OneToMany(mappedBy = "book")
    val readingStatuses: List<ReadingStatus> = emptyList(),

)
