package com.fussionlabs.ordermanagement.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

/**
 * Security configuration class for setting up web security in a Spring application.
 *
 * This class is annotated with `@Configuration` and `@EnableWebSecurity` to enable web security support
 * and to define beans for security configurations, including HTTP security and user details service.
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${spring.security.user.name}")
    private lateinit var username: String

    @Value("\${spring.security.user.password}")
    private lateinit var password: String

    @Value("\${spring.security.user.roles}")
    private lateinit var roles: String

    /**
     * Configures the security filter chain for HTTP security.
     *
     * This method sets up security configurations for HTTP requests, including authorization rules,
     * and HTTP basic authentication.
     *
     * @param http The `HttpSecurity` object to configure.
     * @return The configured `SecurityFilterChain`.
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/orders/**").authenticated()
                    .anyRequest().permitAll()
            }
            .httpBasic(Customizer.withDefaults())
            .csrf{ csrf -> csrf.disable() }
        return http.build()
    }

    /**
     * Configures an in-memory user details service.
     *
     * This method creates a user with the provided username, password, and roles
     * and sets up an in-memory user details service with this user.
     *
     * @return The configured `UserDetailsService`.
     */
    @Bean
    fun userDetailsService(): UserDetailsService {
        val user = User.withDefaultPasswordEncoder()
            .username(username)
            .password(password)
            .roles(*roles.split(",").toTypedArray())
            .build()

        return InMemoryUserDetailsManager(user)
    }
}