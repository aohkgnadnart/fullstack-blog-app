package com.springboot.blog.repository;

import com.springboot.blog.entity.PostRevision;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRevisionRepository extends JpaRepository<PostRevision, Long> {
    Page<PostRevision> findByPostId(Long postId, Pageable pageable);
    List<PostRevision> findByPostIdOrderByCreatedAtAsc(Long postId);
} 