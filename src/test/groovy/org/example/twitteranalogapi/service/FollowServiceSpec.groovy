package org.example.twitteranalogapi.service

import org.example.twitter.api.entity.Follow
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.FollowRepository
import org.example.twitter.api.service.FollowService
import org.springframework.security.core.Authentication
import spock.lang.Specification

class FollowServiceSpec extends Specification {

    FollowRepository followRepository = Mock()
    FollowService followService = new FollowService(followRepository)

    def "follow user increases following count"() {
        given:
        Follow follow = new Follow()

        when:
        followService.saveFollow(follow)

        then:
        1 * followRepository.save(_) >> follow
    }

    def "unfollow user decreases following count"() {
        given:
        Long followId = 1L

        when:
        followService.deleteFollow(followId)

        then:
        1 * followRepository.deleteById(followId)
    }

}