package org.example.twitter.api.repository

import org.example.twitter.api.entity.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository extends JpaRepository<Post, Long> {
}