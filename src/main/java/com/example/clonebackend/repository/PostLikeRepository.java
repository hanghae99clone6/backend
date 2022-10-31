package com.example.clonebackend.repository;


import com.example.clonebackend.domain.Member;
import com.example.clonebackend.domain.Post;
import com.example.clonebackend.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
  Optional<PostLike> findByMemberAndPost(Member member, Post post);
  List<PostLike> findAllByPost(Post post);
  List<PostLike> findAllByMember(Member member);
}
