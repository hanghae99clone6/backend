package com.example.clonebackend.service;


import com.example.clonebackend.controller.response.CommentResponseDto;
import com.example.clonebackend.controller.response.PostResponseDto;
import com.example.clonebackend.controller.response.ResponseDto;
import com.example.clonebackend.domain.Comment;
import com.example.clonebackend.domain.Member;
import com.example.clonebackend.domain.Post;
import com.example.clonebackend.domain.PostLike;
import com.example.clonebackend.jwt.TokenProvider;
import com.example.clonebackend.repository.CommentRepository;
import com.example.clonebackend.repository.PostLikeRepository;
import com.example.clonebackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

  private final PostLikeRepository postLikeRepository;

  private final PostRepository postRepository;
  private final CommentRepository commentRepository;


  private final TokenProvider tokenProvider;
  private final PostService postService;


  @Transactional
  public ResponseDto<?> likePost(Long id, HttpServletRequest request) {
    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "refresh token is invalid");
    }

    Post post = postService.isPresentPost(id);
    if (null == post) {
      return ResponseDto.fail("NOT_FOUND", "post id is not exist");
    }

    PostLike postLike = isPresentPostLike(member, post);
    if (null == postLike) {
      postLikeRepository.save(
          PostLike.builder()
              .member(member)
              .post(post)
              .build()
      );
      return ResponseDto.success("like success");
    } else {
      postLikeRepository.delete(postLike);
      return ResponseDto.success("cancel like success");
    }
  }


  @Transactional(readOnly = true)
  public ResponseDto<?> getAllLikedPost(HttpServletRequest request) {
    Member member = validateMember(request);
    if (null == member) {
      return ResponseDto.fail("INVALID_TOKEN", "refresh token is invalid");
    }

    List<PostLike> postLikeList = postLikeRepository.findAllByMember(member);
    List<PostResponseDto> postResponseDtoList = new ArrayList<>();
    for (PostLike postLike : postLikeList) {
      Post post = postLike.getPost();

      List<Comment> commentList = commentRepository.findAllByPost(post);
      List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
      for (Comment comment : commentList) {

        commentResponseDtoList.add(
            CommentResponseDto.builder()
                .id(comment.getId())
                .author(comment.getMember().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .modifiedAt(comment.getModifiedAt())
                .build()
        );

        postResponseDtoList.add(
            PostResponseDto.builder()
                .postId(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .name(post.getMember().getNickname())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build()
        );
      }
    }

    return ResponseDto.success(postResponseDtoList);
  }



  @Transactional(readOnly = true)
  public PostLike isPresentPostLike(Member member, Post post) {
    Optional<PostLike> optionalPostLike = postLikeRepository.findByMemberAndPost(member, post);
    return optionalPostLike.orElse(null);
  }


  @Transactional
  public Member validateMember(HttpServletRequest request) {
    if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
      return null;
    }
    return tokenProvider.getMemberFromAuthentication();
  }
}
