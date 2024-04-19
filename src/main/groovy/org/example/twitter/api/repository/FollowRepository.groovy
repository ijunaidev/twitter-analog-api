package org.example.twitter.api.repository

import org.example.twitter.api.entity.Follow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository extends JpaRepository<Follow, Long> {
}