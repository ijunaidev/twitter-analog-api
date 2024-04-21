package org.example.twitter.api.controller

import org.example.twitter.api.dto.AuthResponse
import org.example.twitter.api.entity.User
import org.example.twitter.api.exception.UserAlreadyExistsException
import org.example.twitter.api.service.CustomUserDetailsService
import org.example.twitter.api.service.UserService
import org.example.twitter.api.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.security.core.context.SecurityContextHolder

@RestController
@RequestMapping("/users")
class UserController {

    @Autowired
    private UserService userService

    @Autowired
    private AuthenticationManager authenticationManager

    @Autowired
    private PasswordEncoder passwordEncoder

    @Autowired
    private JwtTokenUtil jwtTokenUtil

    @Autowired
    private CustomUserDetailsService userDetailsService

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {
        if (userService.existsByUsername(user.getUsername())) {
            throw new UserAlreadyExistsException("User with username " + user.getUsername() + " already exists")
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword())
        user.setPassword(encodedPassword)
        return userService.saveUser(user)
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestParam String username, @RequestParam String password) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username)
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            final String token = jwtTokenUtil.generateToken(userDetails)
            boolean isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority() == "ROLE_ADMIN")

            AuthResponse response = new AuthResponse("User logged in successfully", token, isAdmin)
            return ResponseEntity.ok(response)
        } else {
            return ResponseEntity.badRequest().body("Invalid username or password")
        }
    }

    @PostMapping("/logout")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> logoutUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        if (!authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_USER") || r.getAuthority().equals("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Insufficient privileges to perform this action")
        }

        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            SecurityContextHolder.clearContext()
            return ResponseEntity.ok("User logged out successfully")
        } else {

            return ResponseEntity.badRequest().body("No user is logged in")
        }
    }

    @GetMapping('/')
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    List<User> listUsers() {
        userService.findAllUsers()
    }

    @GetMapping('/{id}')
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    User getUser(@PathVariable Long id) {
        userService.findUserById(id)
    }

    @PutMapping('/{id}')
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    User updateUser(@PathVariable Long id, @RequestBody User user) {
        userService.updateUser(id, user)
    }

    @DeleteMapping('/{id}')
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id)
    }
}
