package com.example.clonebackend.service;

import com.example.clonebackend.controller.request.PostRequestDto;
import com.example.clonebackend.controller.response.ResponseDto;
import com.example.clonebackend.domain.Member;
import com.example.clonebackend.domain.Post;
import com.example.clonebackend.jwt.TokenProvider;
import com.example.clonebackend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
//    private final CommentRepository commentRepository;
//    private final LikesRepository likesRepository;

    private final TokenProvider tokenProvider;



    @Transactional
    public ResponseDto<?> createPost(PostRequestDto requestDto, HttpServletRequest request) {

        return ResponseDto.success("임시");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getPost(Long id) {

        return ResponseDto.success("임시");
    }

    @Transactional(readOnly = true)
    public ResponseDto<?> getAllPost(Pageable pageable) {

        return ResponseDto.success("임시");
    }

    @Transactional
    public ResponseDto<?> updatePost(Long id, PostRequestDto requestDto, HttpServletRequest request) {

        return ResponseDto.success("임시");
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
        return ResponseDto.success("게시글을 삭제 했습니다.");
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

}