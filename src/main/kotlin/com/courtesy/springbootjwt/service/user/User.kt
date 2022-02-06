package com.courtesy.springbootjwt.service.user

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

data class User(
        @field:JsonIgnore
        val id: Long? = null,

        @field:NotBlank(message = "Email must not be empty!")
        @field:Email
        val email: String,

        @field:NotBlank(message = "Password must not be empty!")
        var password: String,
)