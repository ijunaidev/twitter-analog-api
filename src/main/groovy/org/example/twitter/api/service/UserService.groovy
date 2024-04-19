package org.example.twitter.api.service

import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService {

    @Autowired
    UserRepository userRepository

    User saveUser(User user) {
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
}