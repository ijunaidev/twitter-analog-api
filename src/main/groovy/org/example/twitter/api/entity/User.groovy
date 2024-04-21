package org.example.twitter.api.entity

import org.springframework.security.core.GrantedAuthority

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.OneToOne

@Entity
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String username
    String password

    @OneToMany(mappedBy = 'author')
    Set<Post> posts

    @OneToMany(mappedBy = 'follower')
    Set<Follow> followers

    @OneToMany(mappedBy = 'following')
    Set<Follow> following

    @OneToOne
    Role role
}