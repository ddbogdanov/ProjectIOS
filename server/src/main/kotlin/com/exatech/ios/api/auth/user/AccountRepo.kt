package com.exatech.ios.api.auth.user

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface AccountRepo: JpaRepository<Account, Int> {

    fun existsByUsername(username: String): Boolean
    fun findOneByUsername(username: String): Optional<Account>
}