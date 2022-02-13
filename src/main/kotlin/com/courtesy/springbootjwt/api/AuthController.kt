package com.courtesy.springbootjwt.api

import com.courtesy.springbootjwt.service.auth.AuthService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
class AuthController(private val authService: AuthService, private val authenticationManager: AuthenticationManager) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody loginRequest: LoginRequest): LoginResponse {
        val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
        )
        return authService.login(loginRequest, authentication)
    }

    @PostMapping("/refresh")
    fun refresh(@Valid @RequestBody refreshRequest: RefreshRequest): RefreshResponse {
        val accessToken = authService.generateNewAccessToken(refreshRequest.refreshToken)
        return RefreshResponse(accessToken)
    }
}

class LoginResponse(
        val accessToken: String,
        val refreshToken: String
)

data class LoginRequest(
        val email: String,
        val password: String
)

// Needs :jackson-module-kotlin dependency
data class RefreshRequest(
        val refreshToken: String
)

data class RefreshResponse(
        val accessToken: String
)