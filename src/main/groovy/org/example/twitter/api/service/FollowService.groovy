package org.example.twitter.api.service

import org.example.twitter.api.entity.Follow
import org.example.twitter.api.repository.FollowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class FollowService {

    @Autowired
    FollowRepository followRepository

    Follow saveFollow(Follow follow) {
        followRepository.save(follow)
    }

    void deleteFollow(Long id) {
        followRepository.deleteById(id)
    }
}
