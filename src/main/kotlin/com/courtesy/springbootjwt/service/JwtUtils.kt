package com.courtesy.springbootjwt.service

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT
import com.courtesy.springbootjwt.service.auth.RefreshToken
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.security.SecureRandom
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.servlet.http.HttpServletRequest


// TODO it's simplified here, but in your actual application secret should be in key vault or at least in properties file
private const val SECRET = "68P[=c#5g}^5{bhja2)rupPs#Jh}`@`;"
private const val JWT_TOKEN_EXPIRATION_SECONDS = 3600L // 1 hour
private const val REFRESH_TOKEN_EXPIRATION_SECONDS = 31556952L // 1 year

@Component
class JwtUtils {
    val secureRandom = SecureRandom()

    fun createJwtToken(username: String, refreshTokenId: String): String {
        try {
            val algorithm: Algorithm = Algorithm.HMAC256(SECRET)
            return JWT.create()
                    .withClaim(JwtClaims.EMAIL.name, username)
                    .withClaim(JwtClaims.ID.name, refreshTokenId)
                    .withIssuer("courtesy")
                    .withExpiresAt(getExpirationDate(JWT_TOKEN_EXPIRATION_SECONDS))
                    .sign(algorithm)
        } catch (exception: JWTCreationException) {
            // Invalid Signing configuration / Couldn't convert Claims.
            throw exception
        }
    }

    fun createRefreshToken(username: String): RefreshToken {
        val bytes = ByteArray(64)
        secureRandom.nextBytes(bytes)

        val token = String(Base64.getEncoder().encode(bytes))

        val expiryDate =
                LocalDateTime.ofInstant(Instant.now().plusSeconds(REFRESH_TOKEN_EXPIRATION_SECONDS), ZoneId.of("UTC"))
        val id = UUID.randomUUID().toString()

        return RefreshToken(id, username, token, expiryDate)
    }

    fun verify(token: String): DecodedJWT? {
        try {
            val algorithm = Algorithm.HMAC256(SECRET)
            val verifier: JWTVerifier = JWT.require(algorithm)
                    .withIssuer("courtesy")
                    .build() //Reusable verifier instance
            return verifier.verify(token)
        } catch (exception: JWTVerificationException) {
            //Invalid signature/claims
            return null
        }
    }
    fun decodeJwtToken(request: HttpServletRequest): DecodedJWT? {
        val jwt = parseJwt(request)
        if (jwt != null) {
            return verify(jwt)
        }
        return null
    }

    private fun parseJwt(request: HttpServletRequest): String? {
        val headerAuth = request.getHeader("Authorization")
        return if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            headerAuth.substring(7, headerAuth.length)
        } else null
    }

    private fun getExpirationDate(expirationInSeconds: Long): Date {
        val localDate = LocalDateTime.now().plusSeconds(expirationInSeconds)
        return Date.from(localDate.atZone(ZoneId.of("UTC")).toInstant())
    }
}

enum class JwtClaims(name: String) {
    EMAIL("email"),
    ID("ID)");

    override fun toString(): String {
        return name
    }
}