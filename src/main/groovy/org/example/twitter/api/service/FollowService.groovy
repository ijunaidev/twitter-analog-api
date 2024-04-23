package org.example.twitter.api.service

import org.example.twitter.api.entity.Follow
import org.example.twitter.api.repository.FollowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class FollowService {

    FollowRepository followRepository

    FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository
    }

    Follow saveFollow(Follow follow) {
        followRepository.save(follow)
    }

    void deleteFollow(Long id) {
        followRepository.deleteById(id)
    }
}
