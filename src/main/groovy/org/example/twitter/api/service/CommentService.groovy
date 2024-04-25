package org.example.twitter.api.service

import org.example.twitter.api.dto.CommentDTO
import org.example.twitter.api.entity.Comment
import org.example.twitter.api.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService {

    CommentRepository commentRepository

    CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository
    }

    CommentDTO saveComment(Comment comment) {
        return populateCommentDetails(commentRepository.save(comment))
    }

    List<CommentDTO> findAllComments() {
        List<Comment> commentsList = commentRepository.findAll()
        List<CommentDTO> commentDtoList = new ArrayList<>()

        for(Comment comment : commentsList) {
            commentDtoList.add(populateCommentDetails(comment))
        }

        return commentDtoList
    }

    CommentDTO findCommentById(Long id) {
        return populateCommentDetails(commentRepository.findById(id).orElse(null))
    }

    CommentDTO updateComment(Long id, Comment comment) {
        Comment existingComment = commentRepository.findById(id).orElse(null)
        if (existingComment) {
            existingComment.text = comment.text
            commentRepository.save(existingComment)
        }
        return populateCommentDetails(existingComment)
    }

    CommentDTO populateCommentDetails(Comment comment) {
        if (comment == null) {
            return
        }
        CommentDTO commentDto = new CommentDTO()
        commentDto.setId(comment.getId())
        commentDto.setText(comment.getText())
        commentDto.setPostId(comment.getPost().getId())
        commentDto.setCommenterId(comment.getCommenter().getId())

        return commentDto
    }

    void deleteComment(Long id) {
        commentRepository.deleteById(id)
    }
}