package org.example.twitter.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

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