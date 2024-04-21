package org.example.twitter.api.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.UserRepository
import org.springframework.security.core.authority.AuthorityUtils

@Service
class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository

    @Autowired
    private PasswordEncoder passwordEncoder

    CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
        if (user == null) {
            throw new UsernameNotFoundException("User not found: $username")
        }

        List<GrantedAuthority> authorities = new ArrayList<>()

        authorities.add(new SimpleGrantedAuthority(user.getRole().getName() as String))

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities)
    }
}
