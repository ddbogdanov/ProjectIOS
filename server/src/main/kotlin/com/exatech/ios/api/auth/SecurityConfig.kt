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
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import java.util.*


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
        val corsConfig = CorsConfiguration()
        corsConfig.allowedOrigins = listOf("http://localhost:8080")
        corsConfig.allowedMethods = listOf("GET", "POST", "PUT", "DELETE")

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfig)

        return source
    }
}