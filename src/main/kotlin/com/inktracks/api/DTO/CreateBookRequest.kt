package com.inktracks.api.DTO

data class CreateBookRequest(
    val volumeId: String,
    val title:    String,
    val author:   String
)
