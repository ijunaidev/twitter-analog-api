package org.example.twitter.api.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity
class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @ManyToOne
    @JoinColumn(name = 'follower_id')
    User follower

    @ManyToOne
    @JoinColumn(name = 'following_id')
    User following
}