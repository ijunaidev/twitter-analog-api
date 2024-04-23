package org.example.twitter.api.controller

import org.example.twitter.api.entity.Like
import org.example.twitter.api.service.LikeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/likes")
class LikeController {

    @Autowired
    private LikeService likeService

    @PostMapping('/')
    Like createLike(@RequestBody Like like) {
        likeService.saveLike(like)
    }

    @DeleteMapping('/{id}')
    void deleteLike(@PathVariable Long id) {
        likeService.deleteLike(id)
    }
}
