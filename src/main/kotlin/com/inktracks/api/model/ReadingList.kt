package com.inktracks.api.model

import jakarta.persistence.*

@Entity
@Table(name = "reading_list")
data class ReadingList(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val name: String,

    @ManyToOne
    val user: User,


)