package org.example.twitter.api.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
class CommentDTO {
    Long id
    String text
    Long postId
    Long commenterId
}
