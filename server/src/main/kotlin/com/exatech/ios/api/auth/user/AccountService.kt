package com.exatech.ios.api.auth.user

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class AccountService(val db: AccountRepo, val passwordEncoder: PasswordEncoder) {

    fun findAll(): List<Account> = db.findAll()
    fun findOneById(accountId: Int): Optional<Account> = db.findById(accountId)
    fun findOneByUsername(username: String): Optional<Account> = db.findOneByUsername(username)

    fun save(account: Account): Account? {
        if(db.existsByUsername(account.username)) return null

        account.password = passwordEncoder.encode(account.password)
        return db.save(account)
    }
}