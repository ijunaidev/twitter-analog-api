package org.example.twitter.api.controller

import org.example.twitter.api.entity.Comment
import org.example.twitter.api.service.CommentService
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
@RequestMapping("/comments")
class CommentController {

    @Autowired
    private CommentService commentService

    @PostMapping('/')
    Comment createComment(@RequestBody Comment comment) {
        commentService.saveComment(comment)
    }

    @GetMapping('/')
    List<Comment> listComments() {
        commentService.findAllComments()
    }

    @GetMapping('/{id}')
    Comment getComment(@PathVariable Long id) {
        commentService.findCommentById(id)
    }

    @PutMapping('/{id}')
    Comment updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        commentService.updateComment(id, comment)
    }

    @DeleteMapping('/{id}')
    void deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id)
    }
}