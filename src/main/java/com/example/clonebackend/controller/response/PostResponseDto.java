package com.example.clonebackend.controller.response;

import com.example.clonebackend.domain.PostLike;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String imageUrl;
    private String content;
    private String name;
    private int like;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
