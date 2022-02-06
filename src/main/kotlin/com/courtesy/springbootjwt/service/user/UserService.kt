package com.courtesy.springbootjwt.service.user

import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

private const val PASSWORD_MIN_LENGTH = 6

@Service
class UserService(private val userRepository: UserRepository,
                  private val passwordEncoder: PasswordEncoder) {

    fun createUser(user: User) {
        if (user.password.length < PASSWORD_MIN_LENGTH) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Password too short, must be at least $PASSWORD_MIN_LENGTH characters")
        }
        if (checkIfUserExists(user.email)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists")
        }

        user.password = passwordEncoder.encode(user.password)
        userRepository.insertUser(user)
    }

    private fun checkIfUserExists(email: String): Boolean {
        return userRepository.getUserByEmail(email) != null
    }
}