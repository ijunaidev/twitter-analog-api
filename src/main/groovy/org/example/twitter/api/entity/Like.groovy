package org.example.twitter.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "post_likes")
class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id

    @ManyToOne
    @JoinColumn(name = 'post_id')
    Post post

    @ManyToOne
    @JoinColumn(name = 'user_id')
    User user
}