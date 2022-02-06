package com.courtesy.springbootjwt.api

import com.courtesy.springbootjwt.conf.auth.UserDetailsImpl
import com.courtesy.springbootjwt.service.user.User
import com.courtesy.springbootjwt.service.user.UserService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/user")
class UserController(private val userService: UserService) {
    @PostMapping("/create")
    fun createUser(@RequestBody @Valid user: User) {
        userService.createUser(user)
    }

    @GetMapping
    fun getUser(): UserDetailsImpl {
        val userDetails = SecurityContextHolder.getContext().authentication.principal as UserDetailsImpl
        return userDetails
    }
}