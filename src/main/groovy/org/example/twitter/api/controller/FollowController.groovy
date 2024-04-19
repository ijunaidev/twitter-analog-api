package org.example.twitter.api.controller

import org.example.twitter.api.entity.Follow
import org.example.twitter.api.service.FollowService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/follows")
class FollowController {

    @Autowired
    private FollowService followService

    @PostMapping('/')
    Follow createFollow(@RequestBody Follow follow) {
        followService.saveFollow(follow)
    }

    @DeleteMapping('/{id}')
    void deleteFollow(@PathVariable Long id) {
        followService.deleteFollow(id)
    }
}