package org.example.twitter.api.repository

import org.example.twitter.api.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username)
}