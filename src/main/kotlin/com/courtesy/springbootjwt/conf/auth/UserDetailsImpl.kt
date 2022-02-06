package com.courtesy.springbootjwt.conf.auth

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User

class UserDetailsImpl(private val id: String, username: String, password: String) : User(
        username, password, mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
) {
    fun getId(): String {
        return id
    }
}