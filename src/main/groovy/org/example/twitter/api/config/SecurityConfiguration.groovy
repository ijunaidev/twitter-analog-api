package org.example.twitter.api.config

import org.example.twitter.api.filter.JwtTokenValidationFilter
import org.springframework.http.HttpMethod
import org.example.twitter.api.repository.UserRepository
import org.example.twitter.api.service.CustomUserDetailsService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfiguration {

    private final UserRepository userRepository

    SecurityConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .mvcMatchers("/users/**").permitAll()
                        .mvcMatchers("/posts/**").permitAll()
                        .mvcMatchers("/comments/**").permitAll()
                        .mvcMatchers("/likes/**").permitAll()
                        .mvcMatchers("/follows/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(authenticationEntryPoint())
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .csrf().disable()
                .addFilterBefore(jwtTokenValidationFilter(), UsernamePasswordAuthenticationFilter.class)

        return http.build()
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return new MyAuthenticationEntryPoint()
    }

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserDetailsService(userRepository)
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder()
    }

    @Bean(name = "authenticationManagerBean")
    AuthenticationManager authenticationManagerBean() throws Exception {
        return { authentication -> authentication } as AuthenticationManager
    }

    @Bean
    public JwtTokenValidationFilter jwtTokenValidationFilter() throws Exception {
        return new JwtTokenValidationFilter(authenticationManagerBean())
    }
}
