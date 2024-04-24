package org.example.twitteranalogapi.service

import org.example.twitter.api.dto.PostDTO
import org.example.twitter.api.entity.User
import org.example.twitter.api.service.PostService
import spock.lang.Specification
import org.example.twitter.api.entity.Post
import org.example.twitter.api.repository.PostRepository

class PostServiceSpec extends Specification {

    PostRepository postRepository = Mock()
    PostService postService = new PostService(postRepository)

    def "savePost should save and return the post"() {
        given:
        Long userId = 1L
        User user = new User(id: userId, username: "user")
        Post post = new Post(id: 1, content: "Hello, world!", author: user)

        when:
        postService.savePost(post)

        then:
        1 * postRepository.save(post) >> post
    }

    def "findAllPosts should return all posts"() {
        given:
        Long userId = 1L
        User user = new User(id: userId, username: "user")
        List<Post> expectedPosts = [
                new Post(id: 1, content: "Post 1", author: user),
                new Post(id: 2, content: "Post 2", author: user)
        ]
        postRepository.findAll() >> expectedPosts

        when:
        List<PostDTO> posts = postService.findAllPosts()

        then:
        posts.size() == 2
    }

    def "findPostById should return the post if it exists"() {
        given:
        Long userId = 1L
        User user = new User(id: userId, username: "user")
        Long postId = 1L
        Post expectedPost = new Post(id: postId, content: "Existing post", author: user)
        postRepository.findById(postId) >> Optional.of(expectedPost)

        when:
        PostDTO post = postService.findPostById(postId)

        then:
        post.id == expectedPost.id
        post.authorId == expectedPost.getAuthor().getId()
        post.content == expectedPost.content
    }

    def "updatePost should update the content of an existing post"() {
        given:
        Long userId = 1L
        User user = new User(id: userId, username: "user")
        Long postId = 1L
        Post existingPost = new Post(id: postId, content: "Old content",author: user)
        Post updatedPost = new Post(content: "New content", author: user)
        postRepository.findById(postId) >> Optional.of(existingPost)

        when:
        PostDTO result = postService.updatePost(postId, updatedPost)

        then:
        1 * postRepository.save(existingPost)
        result.content == "New content"
    }

    def "deletePost should delete the post by id"() {
        given:
        Long postId = 1L

        when:
        postService.deletePost(postId)

        then:
        1 * postRepository.deleteById(postId)
    }
}
