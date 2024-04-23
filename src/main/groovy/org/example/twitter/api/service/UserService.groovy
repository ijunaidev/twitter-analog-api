package org.example.twitter.api.service

import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.UserRepository
@Service
class UserService {

    UserRepository userRepository


    UserService(UserRepository userRepository) {
        this.userRepository = userRepository
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

import org.springframework.stereotype.Service
