package org.example.twitter.api.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    String text

    @ManyToOne
    @JoinColumn(name = 'post_id')
    Post post

    @ManyToOne
    @JoinColumn(name = 'user_id')
    User commenter
}