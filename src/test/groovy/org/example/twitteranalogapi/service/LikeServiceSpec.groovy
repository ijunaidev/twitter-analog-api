package org.example.twitteranalogapi.service

import org.example.twitter.api.entity.Like
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.LikeRepository
import org.example.twitter.api.service.LikeService
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import spock.lang.Specification

@SpringBootTest
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

    def "test canUserDeleteLike method"() {
        given:
        User user = new User(username: "testUser", password: "password")
        Like like = new Like(user: user)
        like.id = 1L

        Authentication auth = Mock()
        auth.getName() >> "testUser"

        when:
        boolean canDelete = likeService.canUserDeleteLike(auth, 1L)

        then:
        1 * likeRepository.findById(1L) >> Optional.of(like)
        canDelete == true
    }
}