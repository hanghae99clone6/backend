package com.example.clonebackend.controller;


import com.example.clonebackend.controller.request.CommentRequestDto;
import com.example.clonebackend.controller.response.ResponseDto;
import com.example.clonebackend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
@CustomBaseControllerAnnotation
public class CommentController {

  private final CommentService commentService;

  @RequestMapping(value = "/auth/comment", method = RequestMethod.POST)
  public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                      HttpServletRequest request) {
    return commentService.createComment(requestDto, request);
  }

  @RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
  public ResponseDto<?> getAllComments(@PathVariable Long id) {
    return commentService.getAllCommentsByPost(id);
  }

  @RequestMapping(value = "/auth/comment/{id}", method = RequestMethod.PUT)
  public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
      HttpServletRequest request) {
    return commentService.updateComment(id, requestDto, request);
  }

  @RequestMapping(value = "/auth/comment/{id}", method = RequestMethod.DELETE)
  public ResponseDto<?> deleteComment(@PathVariable Long id,
      HttpServletRequest request) {
    return commentService.deleteComment(id, request);
  }

  @RequestMapping(value = "/auth/comment", method = RequestMethod.GET)
  public ResponseDto<?> getAllCommentByMember(HttpServletRequest request) {
    return commentService.getAllCommentsByMember(request);
  }
}
