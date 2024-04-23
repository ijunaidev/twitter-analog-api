package org.example.twitter.api.service

import org.example.twitter.api.entity.Comment
import org.example.twitter.api.repository.CommentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class CommentService {

    CommentRepository commentRepository

    CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository
    }

    Comment saveComment(Comment comment) {
        commentRepository.save(comment)
    }

    List<Comment> findAllComments() {
        commentRepository.findAll()
    }

    Comment findCommentById(Long id) {
        commentRepository.findById(id).orElse(null)
    }

    Comment updateComment(Long id, Comment comment) {
        Comment existingComment = findCommentById(id)
        if (existingComment) {
            existingComment.text = comment.text
            commentRepository.save(existingComment)
        }
        return existingComment
    }

    void deleteComment(Long id) {
        commentRepository.deleteById(id)
    }
}