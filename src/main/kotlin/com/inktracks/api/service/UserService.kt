package com.inktracks.api.service

import com.inktracks.api.model.User
import com.inktracks.api.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun save(user: User): User = userRepository.save(user)

    fun findById(id: Long): User? = userRepository.findById(id).orElse(null)

    fun findByEmail(email: String): User? = userRepository.findByEmail(email)

    fun findByUsername(username: String): User? = userRepository.findByUsername(username)

    fun findAll(): List<User> = userRepository.findAll()

}
