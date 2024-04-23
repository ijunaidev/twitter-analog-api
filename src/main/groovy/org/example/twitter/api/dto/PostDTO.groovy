package org.example.twitter.api.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class PostDTO {
    Long id
    String content
    Long authorId
    List<CommentDTO> comments
    List<LikeDTO> likes
}