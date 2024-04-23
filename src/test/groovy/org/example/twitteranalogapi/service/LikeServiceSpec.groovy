package org.example.twitteranalogapi.service

import org.example.twitter.api.entity.Like
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.LikeRepository
import org.example.twitter.api.service.LikeService
import org.springframework.security.core.Authentication
import spock.lang.Specification

class LikeServiceSpec extends Specification {

    LikeRepository likeRepository = Mock()
    LikeService likeService = new LikeService(likeRepository)

    def "save like increases like count"() {
        given:
        Like like = new Like()

        when:
        likeService.saveLike(like)

        then:
        1 * likeRepository.save(_) >> like
    }

    def "delete like decreases like count"() {
        given:
        Long likeId = 1L

        when:
        likeService.deleteLike(likeId)

        then:
        1 * likeRepository.deleteById(likeId)
    }
}