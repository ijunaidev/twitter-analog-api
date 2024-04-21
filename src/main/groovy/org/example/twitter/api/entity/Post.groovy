package org.example.twitter.api.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

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