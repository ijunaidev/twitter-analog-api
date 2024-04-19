package org.example.twitter.api.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

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
}