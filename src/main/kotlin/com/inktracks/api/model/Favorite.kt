package com.inktracks.api.model

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@Entity
@Table(name = "favorites")
data class Favorite(
    @EmbeddedId
    val id: FavoriteId,

    @ManyToOne @MapsId("userId")
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne @MapsId("bookId")
    @JoinColumn(name = "book_id")
    val book: Book,

)

@Embeddable
data class FavoriteId(
    val userId: Long = 0,
    val bookId: Long,
) : Serializable
