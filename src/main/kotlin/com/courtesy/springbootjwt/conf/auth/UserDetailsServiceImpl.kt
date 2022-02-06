package com.courtesy.springbootjwt.conf.auth

import com.courtesy.springbootjwt.service.user.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {
    var logger: Logger = LoggerFactory.getLogger(UserDetailsServiceImpl::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Username: $username")
        if (username == null) {
            throw HttpClientErrorException(HttpStatus.BAD_REQUEST)
        }

        val user = userRepository.getUserByEmail(username)
                ?: throw UsernameNotFoundException("user not found")
        return UserDetailsImpl(
                "",
                user.email,
                user.password
        )
    }
}