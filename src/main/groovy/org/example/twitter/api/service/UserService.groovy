package org.example.twitter.api.service

import org.example.twitter.api.entity.Role
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.RoleRepository
import org.example.twitter.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    UserRepository userRepository

    RoleRepository roleRepository

    UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository
        this.roleRepository = roleRepository
    }

    User saveUser(User user) {
        // Retrieve the user role from the RoleRepository
        Optional<Role> optionalUserRole = roleRepository.findByName("ROLE_USER")
        if (!optionalUserRole.isPresent()) {
            throw new RuntimeException("ROLE_USER not found")
        }
        Role userRole = optionalUserRole.get()

        // Set the user role
        user.setRole(userRole)

        // Clear other associations to avoid issues with Hibernate
        user.setFollowers(null)
        user.setFollowing(null)
        user.setPosts(null)

        // Save the user
        userRepository.save(user)
    }


    List<User> findAllUsers() {
        userRepository.findAll()
    }

    User findUserById(Long id) {
        userRepository.findById(id).orElse(null)
    }

    User updateUser(Long id, User user) {
        User existingUser = findUserById(id)
        if (existingUser) {
            existingUser.username = user.username
            existingUser.password = user.password
            userRepository.save(existingUser)
        }
        return existingUser
    }

    void deleteUser(Long id) {
        userRepository.deleteById(id)
    }

    boolean existsByUsername(String username) {
        User existingUser = userRepository.findByUsername(username)
        return existingUser != null
    }
}