package org.example.twitteranalogapi.service

import org.example.twitter.api.entity.Follow
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.FollowRepository
import org.example.twitter.api.service.FollowService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import spock.lang.Specification

@SpringBootTest
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

    def "user can unfollow if they initiated the follow"() {
        given:
        Long followId = 1L
        User follower = new User(username: "follower")
        Follow follow = new Follow(id: followId, follower: follower)
        Authentication auth = Mock(Authentication)
        auth.getName() >> "follower"

        when:
        boolean result = followService.canUserUnfollow(auth, followId)

        then:
        1 * followRepository.findById(followId) >> Optional.of(follow)
        result == true
    }

}