package org.example.twitter.api.config

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtService
    private final UserDetailsService userDetailsService

    JwtAuthenticationFilter(JwtTokenService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService
        this.userDetailsService = userDetailsService
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, java.io.IOException {
        final String header = request.getHeader("Authorization")
        final String jwtToken
        final String username

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response)
            return
        }

        jwtToken = header.substring(7)
        username = jwtService.extractUsername(jwtToken)

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username)
            if (jwtService.isTokenValid(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                )

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request))
                SecurityContextHolder.getContext().setAuthentication(authToken)
            }
        }
        filterChain.doFilter(request, response)
    }
}
