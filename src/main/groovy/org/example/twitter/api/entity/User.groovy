package org.example.twitter.api.entity

import org.example.twitter.api.enums.UserRole
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne

@Entity
class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id
    String firstname
    String lastname
    String email
    String username
    String password

    @OneToMany(mappedBy = 'author')
    Set<Post> posts

    @OneToMany(mappedBy = 'follower')
    Set<Follow> followers

    @OneToMany(mappedBy = 'following')
    Set<Follow> following

    //@OneToOne
    @Enumerated(EnumType.STRING)
    UserRole userRole

//    @OneToOne
//    Role role

    @Override
    Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(userRole.name()))
    }

    @Override
    String getUsername() {
        return username
    }

    @Override
    String getPassword() {
        return password
    }

    @Override
    boolean isAccountNonExpired() {
        return true
    }

    @Override
    boolean isAccountNonLocked() {
        return true
    }

    @Override
    boolean isCredentialsNonExpired() {
        return true
    }

    @Override
    boolean isEnabled() {
        return true
    }
}