package org.example.twitter.api.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import io.jsonwebtoken.security.Keys

import java.security.Key
import java.util.function.Function

@Component
class JwtTokenUtil {

    @Value('${jwt.secret}')
    private String secret

    @Value('${jwt.expiration}')
    private Long expiration

    String generateToken(UserDetails userDetails) {
        def claims = [:]
        claims.put("role", userDetails.authorities.toString())

        return createToken(claims as Map<String, Object>, userDetails.username)
    }

    String extractUsername(String token) {
        return extractClaim(token, { Claims claims -> claims.subject })
    }

    Date extractExpiration(String token) {
        return extractClaim(token, { Claims claims -> claims.expiration })
    }

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact()
    }

    Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date())
    }
}
