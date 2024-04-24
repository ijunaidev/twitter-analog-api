package org.example.twitter.api.service

import org.example.twitter.api.dto.CommentDTO
import org.example.twitter.api.dto.FollowDTO
import org.example.twitter.api.dto.LikeDTO
import org.example.twitter.api.dto.PostDTO
import org.example.twitter.api.dto.UserDTO
import org.example.twitter.api.entity.Comment
import org.example.twitter.api.entity.Follow
import org.example.twitter.api.entity.Like
import org.example.twitter.api.entity.Post
import org.example.twitter.api.entity.User
import org.example.twitter.api.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class UserService {

    UserRepository userRepository

    PasswordEncoder encoder

    UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository
        this.encoder = encoder
    }

    List<UserDTO> findAllUsers() {
        List<User> users = userRepository.findAll()
        List<UserDTO> usersList = new ArrayList<>()
        for (User user : users) {
            usersList.add(populateUserDetails(user))
        }
        return usersList
    }

    UserDTO populateUserDetails(User user) {
        if (user == null) {
            return null
        }
        List<PostDTO> postsList = new ArrayList<>()
        List<FollowDTO> followersList = new ArrayList<>()
        List<FollowDTO> followingList = new ArrayList<>()

        UserDTO userDto = new UserDTO()
        userDto.setId(user.id)
        userDto.setUsername(user.username)
        userDto.setEmail(user.email)
        userDto.setPassword(user.password)
        userDto.setFirstname(user.firstname)
        userDto.setLastname(user.lastname)

        for (Post post : user.getPosts()) {
            List<CommentDTO> commentList = new ArrayList<>()
            List<LikeDTO> likeList = new ArrayList<>()
            PostDTO postDto = new PostDTO()
            postDto.setId(post.getId())
            postDto.setContent(post.getContent())
            postDto.setAuthorId(post.getAuthor().getId())

            for (Comment comment : post.getComments()) {
                CommentDTO commentDto = new CommentDTO()
                commentDto.setId(comment.getId())
                commentDto.setText(comment.getText())
                commentDto.setPostId(comment.getPost().getId())
                commentDto.setCommenterId(comment.getCommenter().getId())

                commentList.add(commentDto)
            }

            for (Like like : post.getLikes()) {
                LikeDTO likeDto = new LikeDTO()
                likeDto.setId(like.getId())
                likeDto.setPostId(like.getPost().getId())
                likeDto.setUserId(like.getUser().getId())

                likeList.add(likeDto)
            }
            postDto.setComments(commentList)
            postDto.setLikes(likeList)
            postsList.add(postDto)
        }

        for(Follow follower : user.getFollowers()) {
            FollowDTO followDTO = new FollowDTO()
            followDTO.setId(follower.getId())
            followDTO.setFollowerId(follower.getFollower().getId())

            followersList.add(followDTO)
        }

        for(Follow following : user.getFollowing()) {
            FollowDTO followDTO = new FollowDTO()
            followDTO.setId(following.getId())
            followDTO.setFollowingId(following.getFollowing().getId())

            followingList.add(followDTO)
        }
        userDto.setPosts(postsList)
        return userDto
    }

    UserDTO findUserById(Long id) {
        User user = userRepository.findById(id).orElse(null)
        return populateUserDetails(user)
    }

    UserDTO updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null)
        if (existingUser) {
            existingUser.username = user.username
            existingUser.password = encoder.encode(user.password)
            existingUser.firstname = user.firstname
            existingUser.lastname = user.lastname
            userRepository.save(existingUser)
        }
        return populateUserDetails(existingUser)
    }

    void deleteUser(Long id) {
        userRepository.deleteById(id)
    }

    boolean existsByUsername(String username) {
        User existingUser = userRepository.findByUsername(username)
        return existingUser != null
    }
}

import org.springframework.stereotype.Service
