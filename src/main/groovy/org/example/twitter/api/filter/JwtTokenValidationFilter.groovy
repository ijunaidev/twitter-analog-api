package org.example.twitter.api.filter

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException
import java.security.Key;
import java.util.Collections;
import io.jsonwebtoken.security.Keys

public class JwtTokenValidationFilter extends BasicAuthenticationFilter {

    @Value('${jwt.secret}') // Make sure to inject your JWT secret key
    private String jwtSecret;

    public JwtTokenValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.replace("Bearer ", "");
            // Validate and authenticate token
            if (isValidToken(token)) {
                // Extract user details from token and set authentication
                UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    public boolean isValidToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | MalformedJwtException e) {
            // Token is expired or malformed
            return false;
        }
    }

    public UsernamePasswordAuthenticationToken getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes())).build().parseClaimsJws(token).getBody();
        String username = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (claims.get("role") != null) {
            authorities.add(new SimpleGrantedAuthority(claims.get("role").toString()));
        }
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
