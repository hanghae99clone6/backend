package com.example.clonebackend.repository;



import com.example.clonebackend.domain.Comment;
import com.example.clonebackend.domain.Member;
import com.example.clonebackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  List<Comment> findAllByPost(Post post);
  List<Comment> findAllByMember(Member member);
  int countAllByPost(Post post);
}
