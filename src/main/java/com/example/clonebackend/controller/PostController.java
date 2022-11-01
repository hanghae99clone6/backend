package com.example.clonebackend.controller;

import com.example.clonebackend.configuration.SwaggerAnnotation;
import com.example.clonebackend.controller.request.PostRequestDto;
import com.example.clonebackend.controller.response.ResponseDto;
import com.example.clonebackend.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@CustomBaseControllerAnnotation
public class PostController {

    private final PostService postService;

    // 게시글 작성
    @SwaggerAnnotation
    @PostMapping(value = "/auth/posts")
    public ResponseDto<?> createPosts(@RequestBody PostRequestDto requestDto,
                                      HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }


    // 모든 게시물 조회
    @GetMapping(value = "/post")
    // @Pagable을 통해 보여줄 페이시 위치(0이 시작), 한 페이지에 게시글 개수(15), 정렬 기준(createdAt), 정렬 기준의 순서(내림차순)을 정의
    public ResponseDto<?> getAllPosts(@PageableDefault(page = 0, size = 15, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.getAllPost(pageable);
    }

    // 게시물 상세조회
    @GetMapping(value = "/post/{id}")
    public ResponseDto<?> getPosts(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 게시글 수정
    @SwaggerAnnotation
    @PutMapping(value = "/auth/post/{id}")
    public ResponseDto<?> updatePosts(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
                                      HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

    //게시글 삭제
    @SwaggerAnnotation
    @DeleteMapping(value = "/auth/post/{id}")
    public ResponseDto<?> deletePosts(@PathVariable Long id,
                                      HttpServletRequest request) {
        return postService.deletePost(id, request);
    }

}