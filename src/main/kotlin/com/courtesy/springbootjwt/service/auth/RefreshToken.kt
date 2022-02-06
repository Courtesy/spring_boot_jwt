package com.courtesy.springbootjwt.service.auth

import java.time.LocalDateTime

data class RefreshToken(
        val id: String,
        val username: String,
        val token: String,
        val expiryDate: LocalDateTime
)