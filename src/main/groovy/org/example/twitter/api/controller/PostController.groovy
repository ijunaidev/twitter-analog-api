package org.example.twitter.api.controller

import org.example.twitter.api.dto.PostDTO
import org.example.twitter.api.entity.Post
import org.example.twitter.api.service.PostService
import org.springframework.beans.factory.annotation.Autowired
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
    PostDTO createPost(@RequestBody Post post) {
        postService.savePost(post)
    }

    @GetMapping('/')
    List<PostDTO> listPosts() {
        postService.findAllPosts()
    }

    @GetMapping('/{id}')
    PostDTO getPost(@PathVariable Long id) {
        postService.findPostById(id)
    }

    @PutMapping('/{id}')
    PostDTO updatePost(@PathVariable Long id, @RequestBody Post post) {
        postService.updatePost(id, post)
    }

    @DeleteMapping('/{id}')
    void deletePost(@PathVariable Long id) {
        postService.deletePost(id)
    }
}
