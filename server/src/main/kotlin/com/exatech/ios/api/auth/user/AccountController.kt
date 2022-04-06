package com.exatech.ios.api.auth.user

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class AccountController(val service: AccountService) {

    @GetMapping
    fun getAll(): List<Account> = service.findAll()
    @GetMapping("/{accountId}")
    fun getOneById(@PathVariable accountId: Int): ResponseEntity<Account> = ResponseEntity.of(service.findOneById(accountId))
    @GetMapping("/username/{username}")
    fun getOneByUsername(@PathVariable username: String): ResponseEntity<Account> = ResponseEntity.of(service.findOneByUsername(username))

    @PostMapping()
    fun saveOne(@RequestBody account: Account): ResponseEntity<Account> {
        val savedAccount = service.save(account) ?: return ResponseEntity.status(HttpStatus.CONFLICT).build()
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount)
    }

    //TODO Login mapping that returns bearer token to be used.
}