package com.inktracks.api.repository

import com.inktracks.api.model.Friend
import com.inktracks.api.model.FriendId
import com.inktracks.api.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface FriendRepository : JpaRepository<Friend, FriendId> {
    fun findByUserId(userId: Long): List<Friend>
    fun findByFriendId(friendId: Long): List<Friend>
    fun findByUserAndFriend(user: User, friend: User): Friend?
    @Query("""
        SELECT f FROM Friend f
        WHERE (f.user = :user AND f.friend = :friend)
           OR (f.user = :friend AND f.friend = :user)
    """)
    fun findFriendshipBetween(user: User, friend: User): Friend?
}
