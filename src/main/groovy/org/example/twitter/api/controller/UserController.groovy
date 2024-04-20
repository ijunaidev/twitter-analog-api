package org.example.twitter.api.controller

import org.example.twitter.api.entity.User
import org.example.twitter.api.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService userService

    @PostMapping('/')
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    User createUser(@RequestBody User user) {
        userService.saveUser(user)
    }

    @GetMapping('/')
    @PreAuthorize("hasRole('USER')")
    List<User> listUsers() {
        userService.findAllUsers()
    }

    @GetMapping('/{id}')
    @PreAuthorize("hasRole('USER')")
    User getUser(@PathVariable Long id) {
        userService.findUserById(id)
    }

    @PutMapping('/{id}')
    @PreAuthorize("hasRole('ADMIN')")
    User updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user)
    }

    @DeleteMapping('/{id}')
    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id)
    }
}
