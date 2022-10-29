package com.example.clonebackend.service;

import com.example.clonebackend.jwt.TokenProvider;
import com.example.clonebackend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//@RequiredArgsConstructor
//@Service
//public class KakaoService {
//    private final MemberRepository memberRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final TokenProvider tokenProvider;
//
//
//}
