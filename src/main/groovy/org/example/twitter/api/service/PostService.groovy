package org.example.twitter.api.service

import org.example.twitter.api.entity.Post
import org.example.twitter.api.repository.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PostService {

    @Autowired
    PostRepository postRepository

    Post savePost(Post post) {
        postRepository.save(post)
    }

    List<Post> findAllPosts() {
        postRepository.findAll()
    }

    Post findPostById(Long id) {
        postRepository.findById(id).orElse(null)
    }

    Post updatePost(Long id, Post post) {
        Post existingPost = findPostById(id)
        if (existingPost) {
            existingPost.content = post.content
            postRepository.save(existingPost)
        }
        return existingPost
    }

    void deletePost(Long id) {
        postRepository.deleteById(id)
    }
}