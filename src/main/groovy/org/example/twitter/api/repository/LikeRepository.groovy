package org.example.twitter.api.repository

import org.example.twitter.api.entity.Like
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository extends JpaRepository<Like, Long> {
}