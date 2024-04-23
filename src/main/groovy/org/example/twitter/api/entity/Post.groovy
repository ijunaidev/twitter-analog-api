package org.example.twitter.api.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany

@Entity
class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String content

    @ManyToOne
    @JoinColumn(name = 'user_id')
    User author

    @OneToMany(mappedBy = 'post')
    Set<Comment> comments

    @OneToMany(mappedBy = 'post')
    Set<Like> likes
}