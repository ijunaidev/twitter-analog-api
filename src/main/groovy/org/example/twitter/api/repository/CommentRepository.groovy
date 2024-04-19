package org.example.twitter.api.repository

import org.example.twitter.api.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository extends JpaRepository<Comment, Long> {
}