package com.exatech.ios.api.auth

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
class SecurityConfig(val authService: AuthenticationService): WebSecurityConfigurerAdapter() {

    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder{
        return BCryptPasswordEncoder()
    }


    @Bean
    @Throws(Exception::class)
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity)  {
        http
            .authorizeRequests().antMatchers("/color").hasAnyRole("admin")
            .and()
            .httpBasic()
            .and()
            .cors()
        http.csrf().disable()
        http.headers().frameOptions().disable()
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth
            .eraseCredentials(true)
            .userDetailsService(authService)
            .passwordEncoder(passwordEncoder())
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", CorsConfiguration().applyPermitDefaultValues())
        return source
    }
}