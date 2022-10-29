package com.example.clonebackend.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    // email 아이디
    private String nickname;
    //추가된 name 속성
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
