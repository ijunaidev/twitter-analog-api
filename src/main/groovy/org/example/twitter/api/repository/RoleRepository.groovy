package org.example.twitter.api.repository

import org.example.twitter.api.entity.Role
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepository extends JpaRepository<Role, Long> {
    boolean existsByName(String name)
    Optional<Role> findByName(String name)
}

