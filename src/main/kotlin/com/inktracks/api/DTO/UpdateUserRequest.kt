package com.inktracks.api.DTO

data class UpdateUserRequest(
    val username: String,
    val profilePic: String? = null
)