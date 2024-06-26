package org.example.twitteranalogapi.service

import org.example.twitter.api.entity.User
import org.example.twitter.api.service.CommentService
import spock.lang.Specification
import org.example.twitter.api.entity.Comment
import org.example.twitter.api.repository.CommentRepository
import org.springframework.security.core.Authentication

class CommentServiceSpec extends Specification {

    CommentRepository commentRepository = Mock()
    CommentService commentService = new CommentService(commentRepository)

    def "test saveComment saves a comment"() {
        given:
        Comment comment = new Comment(text: "New Comment")

        when:
        commentService.saveComment(comment)

        then:
        1 * commentRepository.save(comment)
    }

    def "test findAllComments retrieves all comments"() {
        when:
        commentService.findAllComments()

        then:
        1 * commentRepository.findAll()
    }

    def "test findCommentById retrieves specific comment"() {
        given:
        Long commentId = 1L

        when:
        Comment result = commentService.findCommentById(commentId)

        then:
        1 * commentRepository.findById(commentId) >> Optional.of(new Comment(id: commentId))
        result != null
    }


    def "test updateComment updates comment text"() {
        given:
        Long commentId = 1L
        Comment existingComment = new Comment(id: commentId, text: "Old text")
        Comment updatedComment = new Comment(id: commentId, text: "Updated text")
        commentRepository.findById(commentId) >> Optional.of(existingComment)

        when:
        Comment result = commentService.updateComment(commentId, updatedComment)

        then:
        1 * commentRepository.save(existingComment)
        result.text == "Updated text"
    }

    def "test deleteComment deletes a comment"() {
        given:
        Long commentId = 1L

        when:
        commentService.deleteComment(commentId)

        then:
        1 * commentRepository.deleteById(commentId)
    }
}
