package com.courtesy.springbootjwt.service.auth

import com.courtesy.springbootjwt.api.LoginRequest
import com.courtesy.springbootjwt.api.LoginResponse
import com.courtesy.springbootjwt.service.JwtUtils
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthService(
        private val refreshTokenRepository: RefreshTokenRepository,
        private val jwtUtils: JwtUtils
) {
    fun login(loginRequest: LoginRequest, authentication: Authentication): LoginResponse {
        SecurityContextHolder.getContext().authentication = authentication
        val userDetails = authentication.principal as UserDetails

        val refreshToken = jwtUtils.createRefreshToken(userDetails.username)
        refreshTokenRepository.insert(refreshToken)

        val accessToken = jwtUtils.createJwtToken(userDetails.username, refreshToken.id)

        return LoginResponse(accessToken, refreshToken.token)
    }

    fun generateNewAccessToken(refreshTokenStr: String): String {
        val refreshToken =
                refreshTokenRepository.get(refreshTokenStr) ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request")

        return jwtUtils.createJwtToken(refreshToken.username, refreshToken.id)
    }

    fun logout(refreshTokenId: String) {
        return refreshTokenRepository.delete(refreshTokenId)
    }
}