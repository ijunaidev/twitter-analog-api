package org.example.twitteranalogapi.service

import org.example.twitter.api.dto.UserDTO
import org.example.twitter.api.service.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.UserRepository

class UserServiceSpec extends Specification {
    UserRepository userRepository = Mock()
    PasswordEncoder encoder = Mock()
    UserService userService = new UserService(userRepository, encoder)

    def "findAllUsers should return all users"() {
        given:
        List<User> expectedUsers = [
                new User(username: "user1", password: "pass1"),
                new User(username: "user2", password: "pass2")
        ]
        userRepository.findAll() >> expectedUsers

        when:
        List<UserDTO> users = userService.findAllUsers()

        then:
        users.size() == 2
    }

    def "findUserById should return the user if it exists"() {
        given:
        Long userId = 1L
        User expectedUser = new User(id: userId, username: "existinguser", password: "password")
        userRepository.findById(userId) >> Optional.of(expectedUser)

        when:
        UserDTO user = userService.findUserById(userId)

        then:
        user.id == expectedUser.id
        user.username == expectedUser.username
        user.password == expectedUser.password
    }

    def "updateUser should update the user's details"() {
        given:
        Long userId = 1L
        User existingUser = new User(id: userId, username: "olduser", password: "oldpass")
        User updatedInfo = new User(username: "newuser", password: "newpass")
        userRepository.findById(userId) >> Optional.of(existingUser)
        encoder.encode(updatedInfo.password) >> "newpass"

        when:
        UserDTO updatedUser = userService.updateUser(userId, updatedInfo)

        then:
        1 * userRepository.save(existingUser)
        updatedUser.username == "newuser"
        updatedUser.password == "newpass"
    }

    def "deleteUser should remove the user by id"() {
        given:
        Long userId = 1L

        when:
        userService.deleteUser(userId)

        then:
        1 * userRepository.deleteById(userId)
    }

}