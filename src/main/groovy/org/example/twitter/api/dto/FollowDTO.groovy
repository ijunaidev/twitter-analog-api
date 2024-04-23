package org.example.twitter.api.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class FollowDTO {
    Long id
    Long followerId
    Long followingId
}

