package com.inktracks.api.model

import java.io.Serializable
import java.util.Objects

data class FriendId(
    var user: Long = 0,
    var friend: Long = 0
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FriendId) return false
        return user == other.user && friend == other.friend
    }

    override fun hashCode(): Int = Objects.hash(user, friend)
}
