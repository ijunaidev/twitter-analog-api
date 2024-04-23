package org.example.twitter.api.controller

import org.example.twitter.api.entity.User
import org.example.twitter.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService userService

    @Autowired
    private AuthenticationManager authenticationManager

    @Autowired
    private PasswordEncoder passwordEncoder

    @GetMapping('/')
    List<User> listUsers() {
        userService.findAllUsers()
    }

    @GetMapping('/{id}')
    User getUser(@PathVariable Long id) {
        userService.findUserById(id)
    }

    @PutMapping('/{id}')
    User updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user)
    }

    @DeleteMapping('/{id}')
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id)
    }
}
