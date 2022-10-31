package com.example.clonebackend.controller;


import com.example.clonebackend.controller.response.ResponseDto;
import com.example.clonebackend.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@CustomBaseControllerAnnotation
public class LikeController {

  private final LikeService likeService;

  @RequestMapping(value = "/auth/like/post/{id}", method = RequestMethod.POST)
  public ResponseDto<?> likePost(
      @PathVariable Long id,
      HttpServletRequest request
  ) {
    return likeService.likePost(id, request);
  }



  @RequestMapping(value = "/auth/like/post", method = RequestMethod.POST)
  public ResponseDto<?> getAllLikedPost(
      HttpServletRequest request
  ) {
    return likeService.getAllLikedPost(request);
  }


}
