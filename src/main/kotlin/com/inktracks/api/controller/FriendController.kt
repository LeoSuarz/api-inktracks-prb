package com.inktracks.api.controller

import com.inktracks.api.model.Friend
import com.inktracks.api.service.FriendService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/friends")
class FriendController(private val friendService: FriendService) {

    @PostMapping("/send")
    fun sendRequest(
        @RequestParam userId: Long,
        @RequestParam friendId: Long
    ): ResponseEntity<Friend> {
        return try {
            val result = friendService.sendFriendRequest(userId, friendId)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/accept")
    fun acceptRequest(
        @RequestParam userId: Long,
        @RequestParam friendId: Long
    ): ResponseEntity<Friend> {
        return try {
            val result = friendService.acceptFriendRequest(userId, friendId)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @PostMapping("/reject")
    fun rejectRequest(
        @RequestParam userId: Long,
        @RequestParam friendId: Long
    ): ResponseEntity<Friend> {
        return try {
            val result = friendService.rejectFriend(userId, friendId)
            ResponseEntity.ok(result)
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    // NUEVO ENDPOINT: Eliminar amigo
    @DeleteMapping("/remove")
    fun removeFriend(
        @RequestParam userId: Long,
        @RequestParam friendId: Long
    ): ResponseEntity<Map<String, String>> {
        return try {
            friendService.removeFriend(userId, friendId)
            ResponseEntity.ok(mapOf("message" to "Friendship removed successfully"))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("error" to e.message.orEmpty()))
        }
    }


    @GetMapping("/user/{userId}")
    fun listFriends(@PathVariable userId: Long): List<Friend> =
        friendService.getAcceptedFriends(userId)

    // Solicitudes RECIBIDAS (las que debe responder)
    @GetMapping("/pending/{userId}")
    fun getPendingRequests(@PathVariable userId: Long): List<Friend> {
        return friendService.getPendingRequests(userId)
    }

    @GetMapping("/sent/{userId}")
    fun getSentRequests(@PathVariable userId: Long): List<Friend> {
        return friendService.getSentRequests(userId)
    }
}
