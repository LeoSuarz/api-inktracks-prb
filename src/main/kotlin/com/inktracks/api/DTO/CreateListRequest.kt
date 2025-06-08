package com.inktracks.api.DTO


data class CreateListRequest(
    val userId: Long,
    val name: String,
    val description: String?,
    val bookIds: List<Long>
)
