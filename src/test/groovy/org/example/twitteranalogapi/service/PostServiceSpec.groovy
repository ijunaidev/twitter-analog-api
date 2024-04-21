package org.example.twitteranalogapi.service

import org.example.twitter.api.service.PostService
import spock.lang.Specification
import org.example.twitter.api.entity.Post
import org.example.twitter.api.repository.PostRepository

class PostServiceSpec extends Specification {

    PostRepository postRepository = Mock()
    PostService postService = new PostService(postRepository)

    def "savePost should save and return the post"() {
        given:
        Post post = new Post(content: "Hello, world!")

        when:
        postService.savePost(post)

        then:
        1 * postRepository.save(post) >> post
    }

    def "findAllPosts should return all posts"() {
        given:
        List<Post> expectedPosts = [
                new Post(content: "Post 1"),
                new Post(content: "Post 2")
        ]
        postRepository.findAll() >> expectedPosts

        when:
        List<Post> posts = postService.findAllPosts()

        then:
        posts.size() == 2
        posts == expectedPosts
    }

    def "findPostById should return the post if it exists"() {
        given:
        Long postId = 1L
        Post expectedPost = new Post(id: postId, content: "Existing post")
        postRepository.findById(postId) >> Optional.of(expectedPost)

        when:
        Post post = postService.findPostById(postId)

        then:
        post == expectedPost
    }

    def "updatePost should update the content of an existing post"() {
        given:
        Long postId = 1L
        Post existingPost = new Post(id: postId, content: "Old content")
        Post updatedPost = new Post(content: "New content")
        postRepository.findById(postId) >> Optional.of(existingPost)

        when:
        Post result = postService.updatePost(postId, updatedPost)

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
