package com.exatech.ios.api.auth

import com.exatech.ios.api.auth.user.Account
import com.exatech.ios.api.auth.user.AccountRepo
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AuthenticationService(val accountRepo: AccountRepo): UserDetailsService {

    override fun loadUserByUsername(username: String): User {
        val u = accountRepo.findOneByUsername(username)

        if (u.isEmpty) throw UsernameNotFoundException("Account with username: $username not found")

        return createUser(u.get())
    }

    private fun createUser(u: Account): User {
        return User(u.username, u.password, createAuthorities(u))
    }

    private fun createAuthorities(u: Account): Collection<GrantedAuthority> {
        val authorities = ArrayList<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority("ROLE_${u.role}"))
        return authorities
    }
}