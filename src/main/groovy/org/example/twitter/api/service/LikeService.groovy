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

    Like saveLike(Like like) {
        likeRepository.save(like)
    }

    void deleteLike(Long id) {
        likeRepository.deleteById(id)
    }
}
