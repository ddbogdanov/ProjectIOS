package com.exatech.ios.api.auth.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class AccountController(val service: AccountService, val authManager: AuthenticationManager) {

    @GetMapping
    fun getAll(): List<Account> = service.findAll()
    @GetMapping("/{accountId}")
    fun getOneById(@PathVariable accountId: Int): ResponseEntity<Account> = ResponseEntity.of(service.findOneById(accountId))
    @GetMapping("/username/{username}")
    fun getOneByUsername(@PathVariable username: String): ResponseEntity<Account> = ResponseEntity.of(service.findOneByUsername(username))

    @PostMapping("/login")
    fun authenticateUser(@RequestBody account: Account): ResponseEntity<String> {
        val auth: Authentication = authManager.authenticate(UsernamePasswordAuthenticationToken(account.username, account.password))
        SecurityContextHolder.getContext().authentication = auth
        return ResponseEntity<String>("User signed-in successfully", HttpStatus.OK)
    }

    @PostMapping()
    fun saveOne(@RequestBody account: Account): ResponseEntity<Account> {
        val savedAccount = service.save(account) ?: return ResponseEntity.status(HttpStatus.CONFLICT).build()
        savedAccount.role = "admin"
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount)
    }

}