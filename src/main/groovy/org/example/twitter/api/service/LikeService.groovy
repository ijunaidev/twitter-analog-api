package org.example.twitter.api.service

import org.example.twitter.api.entity.Like
import org.example.twitter.api.repository.LikeRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class LikeService {

    LikeRepository likeRepository

    LikeService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository
    }

    boolean canUserDeleteLike(Authentication authentication, Long likeId) {
        Like like = likeRepository.findById(likeId).orElse(null)
        if (like != null && authentication != null) {
            String currentUsername = authentication.getName()
            return like.user.username.equals(currentUsername)
        }
        return false
    }

    Like saveLike(Like like) {
        likeRepository.save(like)
    }

    void deleteLike(Long id) {
        likeRepository.deleteById(id)
    }
}
