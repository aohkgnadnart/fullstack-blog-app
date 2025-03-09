package com.springboot.blog.utils;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.PostRevision;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.PostRevisionRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostRevisionInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(PostRevisionInitializer.class);

    private final PostRepository postRepository;
    private final PostRevisionRepository postRevisionRepository;

    public PostRevisionInitializer(PostRepository postRepository, PostRevisionRepository postRevisionRepository) {
        this.postRepository = postRepository;
        this.postRevisionRepository = postRevisionRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        logger.info("Initializing post revisions...");
        
        // Xử lý những bài viết chỉ có phiên bản UPDATED mà không có CREATED
        fixPostsWithOnlyUpdatedRevisions();
        
        logger.info("Post revisions initialization completed.");
    }

    @Transactional
    private void fixPostsWithOnlyUpdatedRevisions() {
        // Lấy tất cả các bài viết
        List<Post> allPosts = postRepository.findAll();
        
        for (Post post : allPosts) {
            // Lấy tất cả các phiên bản của bài viết, sắp xếp theo thời gian tạo
            List<PostRevision> revisions = postRevisionRepository.findByPostIdOrderByCreatedAtAsc(post.getId());
            
            // Kiểm tra nếu không có phiên bản CREATED
            boolean hasCreatedRevision = revisions.stream()
                    .anyMatch(revision -> revision.getRevisionType() == PostRevision.RevisionType.CREATED);
            
            // Nếu có phiên bản nhưng không có phiên bản CREATED
            if (!revisions.isEmpty() && !hasCreatedRevision) {
                logger.info("Found post (ID: {}) with revisions but no CREATED revision", post.getId());
                
                // Lấy phiên bản đầu tiên và đổi thành CREATED
                PostRevision firstRevision = revisions.get(0);
                firstRevision.setRevisionType(PostRevision.RevisionType.CREATED);
                postRevisionRepository.save(firstRevision);
                
                logger.info("Fixed: Changed first revision (ID: {}) to CREATED for post (ID: {})", 
                        firstRevision.getId(), post.getId());
            }
        }
    }
} 