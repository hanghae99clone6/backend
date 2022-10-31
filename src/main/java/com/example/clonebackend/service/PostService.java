package com.example.clonebackend.service;

import com.example.clonebackend.controller.request.PostRequestDto;
import com.example.clonebackend.controller.response.PostResponseDto;
import com.example.clonebackend.controller.response.ResponseDto;
import com.example.clonebackend.domain.Member;
import com.example.clonebackend.domain.Post;
import com.example.clonebackend.domain.PostLike;
import com.example.clonebackend.error.ErrorCode;
import com.example.clonebackend.jwt.TokenProvider;
import com.example.clonebackend.repository.CommentRepository;
import com.example.clonebackend.repository.PostLikeRepository;
import com.example.clonebackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.clonebackend.controller.response.ResponseDto.success;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostLikeRepository postLikeRepository;

    private final TokenProvider tokenProvider;



    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {

        Member member = validateMember(request);

        Post post = Post.builder()
                .imageUrl(requestDto.getImageUrl())
                .content(requestDto.getContent())
                .member(member)
                .build();
        postRepository.save(post);

        return ResponseDto.success( "success");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {
        Post post = isPresentPost(id);
        if (null == post) {
            return ResponseDto.fail("POST_NOT_FOUND", "게시글이 존재하지 않습니다.");
        }
        return success(
                PostResponseDto.builder()
                        .postId(post.getId())
                        .imageUrl(post.getImageUrl())
                        .content(post.getContent())
                        .name(post.getMember().getName())
                        .like(countLikesPost(post))
                        .createdAt(post.getCreatedAt())
                        .modifiedAt(post.getModifiedAt())
                        .build()
        );
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost(Pageable pageable) {

        Page<Post> postList = postRepository.findAll(pageable);

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {
            postResponseDtoList.add(PostResponseDto.builder()
                    .postId(post.getId())
                    .imageUrl(post.getImageUrl())
                    .content(post.getContent())
                    .name(post.getMember().getName())
                    .like(countLikesPost(post))
                    .createdAt(post.getCreatedAt())
                    .modifiedAt(post.getModifiedAt())
                    .build()
            );
        }

        return success(postResponseDtoList);
    }

    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {

        Post post = isPresentPost(id);
        post.update(requestDto);
        return success("success");

    }

    @Transactional
    public ResponseDto<?> deletePost(Long id, HttpServletRequest request) {
        Member member = validateMember(request);
        if (null == member) {
            throw new RuntimeException();
        }

        Post post = isPresentPost(id);
        if (null == post) {
            throw new RuntimeException();
        }


        if (post.validateMember(member)) {
            throw new RuntimeException();
        }
        postRepository.delete(post);
        return success("success");
    }

    @Transactional(readOnly = true)
    public Post isPresentPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        return optionalPost.orElse(null);
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }
    @Transactional(readOnly = true)
    public int countLikesPost(Post post) {
        List<PostLike> postLikeList = postLikeRepository.findAllByPost(post);
        return postLikeList.size();
    }

}