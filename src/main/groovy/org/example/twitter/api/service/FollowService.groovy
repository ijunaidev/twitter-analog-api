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

//    boolean canUserUnfollow(Authentication authentication, Long followId) {
//        Follow follow = followRepository.findById(followId).orElse(null)
//        if (follow != null && authentication != null) {
//            String currentUsername = authentication.getName()
//            return follow.follower.username.equals(currentUsername)
//        }
//        return false
//    }

    boolean canUserUnfollow(Authentication auth, Long followId) {
        Optional<Follow> followOpt = followRepository.findById(followId);
        if (followOpt.isPresent()) {
            Follow follow = followOpt.get();
            return follow.follower.username.equals(auth.getName());
        }
        return false; // Correctly handle case where follow is not found
    }

    Follow saveFollow(Follow follow) {
        followRepository.save(follow)
    }

    void deleteFollow(Long id) {
        followRepository.deleteById(id)
    }
}
