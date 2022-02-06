package com.courtesy.springbootjwt.conf.auth

import com.courtesy.springbootjwt.service.JwtClaims
import com.courtesy.springbootjwt.service.JwtUtils
import com.courtesy.springbootjwt.service.auth.AuthService
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomLogoutHandler (private val authService: AuthService, private val jwtUtils: JwtUtils): LogoutHandler {
    override fun logout(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val decodedJwt = jwtUtils.decodeJwtToken(request!!)
        if (decodedJwt != null) {
            val refreshTokenId: String = decodedJwt.getClaim(JwtClaims.ID.name).asString()
            authService.logout(refreshTokenId)
        }
    }
}