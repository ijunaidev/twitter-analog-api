package org.example.twitter.api.config

import org.example.twitter.api.entity.Role
import org.example.twitter.api.repository.RoleRepository
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class RoleInitializer {

    final RoleRepository roleRepository

    RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository
    }

    @PostConstruct
    void initRoles() {
        // Check if the roles already exist
        if (!roleRepository.existsByName("ROLE_ADMIN")) {
            roleRepository.save(new Role(name: "ROLE_ADMIN"))
        }
        if (!roleRepository.existsByName("ROLE_USER")) {
            roleRepository.save(new Role(name: "ROLE_USER"))
        }
    }
}
