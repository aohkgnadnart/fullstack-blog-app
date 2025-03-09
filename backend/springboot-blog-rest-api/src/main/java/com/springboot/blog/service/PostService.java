package com.springboot.blog.service;

import com.springboot.blog.payload.PostFullResponseDto;
import com.springboot.blog.payload.PostRequestDto;
import com.springboot.blog.payload.PostPageResponseDto;
import com.springboot.blog.payload.PostRevisionResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {

    PostFullResponseDto createPost(PostRequestDto postRequestDto);
    PostFullResponseDto updatePost(PostRequestDto postRequestDto, long id);
    void deletePostById(long id);

    PostFullResponseDto getPostById(long id);
    PostPageResponseDto getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostPageResponseDto getPostsByTitle(String title, int pageNo, int pageSize, String sortBy, String sortDir);
    PostPageResponseDto getPostsByTagId(long tagId, int pageNo, int pageSize, String sortBy, String sortDir);
    PostPageResponseDto getPostsByUserId(long userId, int pageNo, int pageSize, String sortBy, String sortDir);
    PostPageResponseDto getPostsFromFollowingUsers(String currentUsername, int pageNo, int pageSize, String sortBy, String sortDir);

    void addViewer(long postId, String currentUsername);
    void userViewPost(long postId, String currentUsername);

    boolean hasLike(long postId, String currentUsername);
    boolean hasDislike(long postId, String currentUsername);

    void dislikePost(long postId, String currentUsername);
    void likePost(long postId, String currentUsername);
    void noDislikePost(long postId, String currentUsername);
    void noLikePost(long postId, String currentUsername);

    void userNoLikePost(long postId, String currentUsername);
    void userDislikePost(long postId, String currentUsername);
    void userLikePost(long postId, String currentUsername);
    void userNoDislikePost(long postId, String currentUsername);

    void commentOnPost(long postId);
    void userCommentOnPost(long postId);

    void deleteCommentOnPost(long postId);
    void userDeleteCommentOnPost(long postId);

    // New methods for post revision history
    Page<PostRevisionResponseDto> getPostRevisionHistory(long postId, int pageNo, int pageSize);
    PostRevisionResponseDto getPostRevision(long revisionId);
    String compareRevisions(long revisionId1, long revisionId2);
}
