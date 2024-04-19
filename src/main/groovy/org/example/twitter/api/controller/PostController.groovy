package org.example.twitter.api.controller


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController {
    @GetMapping("/posts")
    String getPosts() {
        return "posts"
    }
}
