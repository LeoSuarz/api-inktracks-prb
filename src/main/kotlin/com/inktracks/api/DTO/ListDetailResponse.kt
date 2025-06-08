package com.inktracks.api.DTO

import java.time.LocalDateTime


data class ListDetailResponse(
    val id: Long,
    val userId: Long,
    val name: String,
    val description: String?,
    val createdAt: LocalDateTime,
    val items: List<BookInListDto>
)