package org.example.twitter.api.service

import org.example.twitter.api.dto.CommentDTO
import org.example.twitter.api.dto.LikeDTO
import org.example.twitter.api.dto.PostDTO
import org.example.twitter.api.entity.Comment
import org.example.twitter.api.entity.Like
import org.example.twitter.api.entity.Post
import org.example.twitter.api.repository.PostRepository
import org.springframework.stereotype.Service

@Service
class PostService {

    PostRepository postRepository

    PostService(PostRepository postRepository) {
        this.postRepository = postRepository
    }

    PostDTO savePost(Post post) {
        return populatePostDetails(postRepository.save(post))
    }

    List<PostDTO> findAllPosts() {
        List<Post> postList = postRepository.findAll()
        List<PostDTO> postDtoList = new ArrayList<>()
        for (Post post : postList) {
            postDtoList.add(populatePostDetails(post))
        }
        return postDtoList
    }

    PostDTO findPostById(Long id) {
        return populatePostDetails(postRepository.findById(id).orElse(null))
    }

    PostDTO updatePost(Long id, Post post) {
        Post existingPost = postRepository.findById(id).orElse(null)
        if (existingPost) {
            existingPost.content = post.content
            postRepository.save(existingPost)
        }
        return populatePostDetails(existingPost)
    }

    void deletePost(Long id) {
        postRepository.deleteById(id)
    }

    PostDTO populatePostDetails(Post post) {
        if (post == null) {
            return null
        }
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

        return postDto
    }
}