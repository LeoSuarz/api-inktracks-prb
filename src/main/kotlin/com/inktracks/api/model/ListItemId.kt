package com.inktracks.api.model

import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class ListItemId(
    val listId: Long = 0,
    val bookId: Long = 0
) : Serializable
