package org.example.twitter.api.auth

import lombok.RequiredArgsConstructor
import org.example.twitter.api.config.JwtTokenService
import org.example.twitter.api.enums.UserRole
import org.example.twitter.api.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.example.twitter.api.entity.User

@Service
class AuthenticationService {

    private final UserRepository repository
    private final PasswordEncoder passwordEncoder
    private final JwtTokenService jwtService
    private final AuthenticationManager authenticationManager

    AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtTokenService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository
        this.passwordEncoder = passwordEncoder
        this.jwtService = jwtService
        this.authenticationManager = authenticationManager
    }

    AuthenticationResponse register(RegisterRequest request) {
        User user = new User()
        user.firstname = request.firstname
        user.lastname = request.lastname
        user.username = request.username
        user.email = request.email
        user.password = passwordEncoder.encode(request.password)
        user.userRole = UserRole.USER

        repository.save(user)

        String token = jwtService.generateToken(user)

        AuthenticationResponse response = new AuthenticationResponse()
        response.token = token

        return response

    }

    AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ))

        User user = repository.findByUsername(request.getUsername()).orElseThrow()
        String token = jwtService.generateToken(user)

        AuthenticationResponse response = new AuthenticationResponse()
        response.token = token

        return response
    }
}
