package org.example.twitter.api.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class LikeDTO {
    Long id
    Long postId
    Long userId
}