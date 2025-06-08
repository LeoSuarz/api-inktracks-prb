package com.inktracks.api.service

import com.inktracks.api.model.*
import com.inktracks.api.repository.FriendRepository
import com.inktracks.api.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.slf4j.LoggerFactory

@Service
@Transactional
class FriendService(
    private val friendRepository: FriendRepository,
    private val userRepository: UserRepository
) {
    private val logger = LoggerFactory.getLogger(FriendService::class.java)

    fun sendFriendRequest(userId: Long, friendId: Long): Friend {
        logger.info("Sending friend request from user $userId to friend $friendId")

        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val friend = userRepository.findById(friendId).orElseThrow { Exception("Friend not found") }

        // Verificar si ya existe una relación
        val existingFriendship = friendRepository.findFriendshipBetween(user, friend)
        if (existingFriendship != null) {
            logger.warn("Friendship already exists between $userId and $friendId")
            throw Exception("Friendship already exists or request already sent")
        }

        val friendship = Friend(user = user, friend = friend, status = FriendStatus.PENDING)
        logger.info("Created new friend request: $friendship")
        return friendRepository.save(friendship)
    }

    fun acceptFriendRequest(userId: Long, friendId: Long): Friend {
        logger.info("Accepting friend request. Requester ID: $userId, Accepter ID: $friendId")

        val requester = userRepository.findById(userId).orElseThrow { Exception("Requester not found") }
        val accepter = userRepository.findById(friendId).orElseThrow { Exception("Accepter not found") }

        // Buscar la solicitud original (donde requester envió solicitud a accepter)
        val friendship = friendRepository.findByUserAndFriend(requester, accepter)
            ?: throw Exception("Friend request not found")

        logger.info("Found request: $friendship with status ${friendship.status}")

        if (friendship.status != FriendStatus.PENDING) {
            logger.warn("Request is not pending, current status: ${friendship.status}")
            throw Exception("Friend request is not pending")
        }

        // Actualizar la solicitud original a ACCEPTED
        val updated = friendship.copy(status = FriendStatus.ACCEPTED)
        friendRepository.save(updated)
        logger.info("Updated original request to ACCEPTED")

        // Crear la relación inversa para que ambos sean amigos
        val inverseFriendship = Friend(
            user = accepter,
            friend = requester,
            status = FriendStatus.ACCEPTED
        )
        friendRepository.save(inverseFriendship)
        logger.info("Created inverse friendship")

        return updated
    }

    fun rejectFriend(userId: Long, friendId: Long): Friend {
        logger.info("Rejecting friend request. Requester ID: $userId, Rejecter ID: $friendId")

        val requester = userRepository.findById(userId).orElseThrow { Exception("Requester not found") }
        val rejecter = userRepository.findById(friendId).orElseThrow { Exception("Rejecter not found") }

        // Buscar la solicitud pendiente (donde requester envió solicitud a rejecter)
        val friendship = friendRepository.findByUserAndFriend(requester, rejecter)
            ?: throw Exception("Friend request not found")

        logger.info("Found request: $friendship with status ${friendship.status}")

        if (friendship.status != FriendStatus.PENDING) {
            logger.warn("Request is not pending, current status: ${friendship.status}")
            throw Exception("Friend request is not pending")
        }

        val updated = friendship.copy(status = FriendStatus.REJECTED)
        logger.info("Updated request to REJECTED")
        return friendRepository.save(updated)
    }

    fun removeFriend(userId: Long, friendId: Long): Boolean {
        logger.info("Removing friendship between user $userId and friend $friendId")

        val user = userRepository.findById(userId).orElseThrow { Exception("User not found") }
        val friend = userRepository.findById(friendId).orElseThrow { Exception("Friend not found") }

        // Buscar ambas direcciones de la amistad
        val friendship1 = friendRepository.findByUserAndFriend(user, friend)
        val friendship2 = friendRepository.findByUserAndFriend(friend, user)

        var deletedCount = 0

        // Eliminar la primera relación si existe y está aceptada
        friendship1?.let { f1 ->
            if (f1.status == FriendStatus.ACCEPTED) {
                friendRepository.delete(f1)
                deletedCount++
                logger.info("Deleted friendship: ${f1.user.username} -> ${f1.friend.username}")
            }
        }

        // Eliminar la segunda relación si existe y está aceptada
        friendship2?.let { f2 ->
            if (f2.status == FriendStatus.ACCEPTED) {
                friendRepository.delete(f2)
                deletedCount++
                logger.info("Deleted friendship: ${f2.user.username} -> ${f2.friend.username}")
            }
        }

        if (deletedCount == 0) {
            logger.warn("No accepted friendship found between $userId and $friendId")
            throw Exception("No friendship found between these users")
        }

        logger.info("Successfully removed friendship between $userId and $friendId")
        return true
    }

    fun listFriends(userId: Long): List<Friend> {
        logger.info("Listing friends for user $userId")
        return friendRepository.findByUserId(userId)
            .filter { it.status == FriendStatus.ACCEPTED }
    }

    fun getPendingRequests(userId: Long): List<Friend> {
        logger.info("Getting pending requests for user $userId")
        return friendRepository.findByFriendId(userId)
            .filter { it.status == FriendStatus.PENDING }
    }

// NUEVO: Solicitudes que el usuario ENVIÓ
    fun getSentRequests(userId: Long): List<Friend> {
        return friendRepository.findByUserId(userId)
            .filter { it.status == FriendStatus.PENDING }
    }

    // NUEVO: Obtener todos los amigos aceptados (método alternativo)
    fun getAcceptedFriends(userId: Long): List<Friend> {
        return friendRepository.findByUserId(userId)
            .filter { it.status == FriendStatus.ACCEPTED }
    }
}
