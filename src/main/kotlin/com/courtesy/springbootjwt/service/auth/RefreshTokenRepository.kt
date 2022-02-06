package com.courtesy.springbootjwt.service.auth

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class RefreshTokenRepository(private val jdbcTemplate: JdbcTemplate) {
    private val rowMapper = RowMapper { rs: ResultSet, rowNum: Int ->
        RefreshToken(
                id = rs.getString("id"),
                username = rs.getString("username"),
                token = rs.getString("token"),
                expiryDate = rs.getTimestamp("expiry_date").toLocalDateTime()
        )
    }

    fun insert(refreshToken: RefreshToken) {
        jdbcTemplate.update(
                "INSERT INTO refresh_token(id, username, token, expiry_date) VALUES (?, ?, ?, ?)",
                refreshToken.id, refreshToken.username, refreshToken.token, refreshToken.expiryDate
        )
    }

    fun get(refreshToken: String): RefreshToken? {
        val sql = "SELECT * FROM refresh_token where token = ?"
        return try {
            jdbcTemplate.queryForObject(sql, rowMapper, refreshToken)
        } catch (e: Exception) {
            null
        }
    }

    fun delete(refreshTokenId: String) {
        jdbcTemplate.update("DELETE FROM refresh_token WHERE id = ?", refreshTokenId)
    }
}