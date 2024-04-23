package org.example.twitter.api.dto

import com.fasterxml.jackson.annotation.JsonInclude
import org.example.twitter.api.enums.UserRole

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserDTO {
    Long id
    String firstname
    String lastname
    String email
    String username
    String password
    UserRole userRole
    List<PostDTO> posts
    List<FollowDTO> followers
    List<FollowDTO> following
}
