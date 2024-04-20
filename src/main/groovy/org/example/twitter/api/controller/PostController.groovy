package org.example.twitter.api.controller

import org.example.twitter.api.entity.Post
import org.example.twitter.api.service.PostService
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
@RequestMapping("/posts")
class PostController {

    @Autowired
    private PostService postService

    @PostMapping('/')
    @PreAuthorize("hasRole('USER')")
    Post createPost(@RequestBody Post post) {
        postService.savePost(post)
    }

    @GetMapping('/')
    @PreAuthorize("hasRole('USER')")
    List<Post> listPosts() {
        postService.findAllPosts()
    }

    @GetMapping('/{id}')
    @PreAuthorize("hasRole('USER')")
    Post getPost(@PathVariable Long id) {
        postService.findPostById(id)
    }

    @PutMapping('/{id}')
    @PreAuthorize("hasRole('USER')")
    Post updatePost(@PathVariable Long id, @RequestBody Post post) {
        postService.updatePost(id, post)
    }

    @DeleteMapping('/{id}')
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    void deletePost(@PathVariable Long id) {
        postService.deletePost(id)
    }
}
