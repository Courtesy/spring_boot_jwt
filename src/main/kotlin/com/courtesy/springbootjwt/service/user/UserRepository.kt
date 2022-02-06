package com.courtesy.springbootjwt.service.user

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class UserRepository(private val jdbcTemplate: JdbcTemplate) {
    private val rowMapper = RowMapper { rs: ResultSet, rowNum: Int ->
        User(id = rs.getLong("id"),
                email = rs.getString("email"),
                password = rs.getString("password")
        )
    }

    fun insertUser(user: User) {
        jdbcTemplate.update("INSERT INTO users(email, password) VALUES (?, ?)", user.email, user.password)
    }

    fun getUserByEmail(email: String): User? {
        val sql = "Select * from Users where email = ?"
        return try {
            jdbcTemplate.queryForObject(sql, rowMapper, email)
        } catch (e: Exception) {
            null
        }
    }
}