package com.inktracks.api.controller

import com.inktracks.api.DTO.UpdateUserRequest
import com.inktracks.api.model.User
import com.inktracks.api.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = ["*"]) // Opcional: permite llamadas desde Android o Postman
class UserController(private val userService: UserService) {

    // Crear nuevo usuario
    @PostMapping
    fun registerUser(@RequestBody user: User): ResponseEntity<User> {
        val existing = userService.findByEmail(user.email)
        if (existing != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
        val savedUser = userService.save(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser)
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.findById(id)
        return if (user != null) ResponseEntity.ok(user)
        else ResponseEntity.notFound().build()
    }

    // Buscar usuario por email
    @GetMapping("/email/{email}")
    fun getUserByEmail(@PathVariable email: String): ResponseEntity<User> {
        val user = userService.findByEmail(email)
        return if (user != null) ResponseEntity.ok(user)
        else ResponseEntity.notFound().build()
    }

    // Buscar usuario por nombre de usuario
    @GetMapping("/username/{username}")
    fun getUserByUsername(@PathVariable username: String): ResponseEntity<User> {
        val user = userService.findByUsername(username)
        return if (user != null) ResponseEntity.ok(user)
        else ResponseEntity.notFound().build()
    }

    //login
    @PostMapping("/login")
    fun login(@RequestBody user: User): ResponseEntity<User> {
        val foundUser = userService.findByEmail(user.email)
        return if (foundUser != null && foundUser.password == user.password) {
            ResponseEntity.ok(foundUser)
        } else {
            ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
        }
    }


    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: Long,
        @RequestBody req: UpdateUserRequest
    ): ResponseEntity<User> {
        val user = userService.findById(id)
            ?: return ResponseEntity.notFound().build()

        val other = userService.findByUsername(req.username)
        if (other != null && other.id != id) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build()
        }

        user.username = req.username

        req.profilePic?.let { user.profilePic = it }


        val saved = userService.save(user)
        return ResponseEntity.ok(saved)
    }
    // Listar todos los usuarios
    @GetMapping
    fun getAllUsers(): List<User> = userService.findAll()


    @GetMapping("/search")
    fun searchByUsername(@RequestParam username: String): User? =
        userService.findByUsername(username)
}
