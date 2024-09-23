package com.springboot.blog.service;

import com.springboot.blog.payload.CommentCreateDto;
import com.springboot.blog.payload.CommentResponseDto;

import java.util.List;

public interface CommentService {

    CommentResponseDto createComment(long postId, CommentCreateDto commentCreateDto, String currentUsername);

    void deleteCommentById(long id);

    CommentResponseDto createReply(long postId, long parentCommentId, CommentCreateDto commentCreateDto, String currentUsername);

    List<CommentResponseDto> getCommentsByPostId(long postId);



    boolean hasLike(long commentId, String currentUsername);
    boolean hasDislike(long commentId, String currentUsername);

    void dislikeComment(long commentId, String currentUsername);
    void likeComment(long commentId, String currentUsername);
    void noDislikeComment(long commentId, String currentUsername);
    void noLikeComment(long commentId, String currentUsername);


    void userNoLikeComment(long commentId, String currentUsername);
    void userDislikeComment(long commentId, String currentUsername);
    void userLikeComment(long commentId, String currentUsername);
    void userNoDislikeComment(long commentId, String currentUsername);
}
