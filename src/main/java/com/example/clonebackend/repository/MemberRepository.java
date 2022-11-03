package com.example.clonebackend.repository;

import com.example.clonebackend.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByName(String name);


}
