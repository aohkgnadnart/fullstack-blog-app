package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.payload.*;
import com.springboot.blog.repository.CommentRepository;
import com.springboot.blog.repository.PostRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.CommentService;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import com.springboot.blog.utils.CommentUtils;
import com.springboot.blog.utils.SecurityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private UserRepository userRepository;

    private ModelMapper mapper;

    private RabbitTemplate rabbitTemplate;

    private PostService postService;

    public CommentServiceImpl(CommentRepository commentRepository,
                              PostRepository postRepository,
                              UserRepository userRepository,
                              ModelMapper mapper,
                              RabbitTemplate rabbitTemplate,
                              PostService postService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
        this.postService = postService;
    }

    @Override
    public CommentResponseDto createComment(long postId, CommentCreateDto commentCreateDto, String currentUsername) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

        Comment comment = mapper.map(commentCreateDto, Comment.class);

        User user = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        comment.setPost(post);
        comment.setUser(user);
        comment.setCreateAt(LocalDateTime.now());

        Comment newComment = commentRepository.save(comment);
        postService.userCommentOnPost(postId);
        return mapper.map(newComment, CommentResponseDto.class);
    }

    @Override
    public void deleteCommentById(long id) {

        // get post by id from database
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(id)));

        // kiem tra xem role cua currentUsernam co phai admin hoac author khong
        if(!SecurityUtils.hasRole(AppConstants.ROLE_ADMIN) && !SecurityUtils.getCurrentUsername().equals(comment.getUser().getUsername())){
            throw new BlogAPIException(HttpStatus.FORBIDDEN, "You do not have permission to delete this post");
        }

        commentRepository.delete(comment);

        postService.userDeleteCommentOnPost(comment.getPost().getId());
    }

    @Override
    public CommentResponseDto createReply(long postId, long parentCommentId, CommentCreateDto commentCreateDto, String currentUsername) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));
        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", Long.toString(parentCommentId)));

        Comment comment = mapper.map(commentCreateDto, Comment.class);
        comment.setParentComment(parentComment);

        User user = userRepository.findByUsername(currentUsername)
                        .orElseThrow(() -> new ResourceNotFoundException("User", "username", currentUsername));

        comment.setPost(post);
        comment.setUser(user);
        comment.setCreateAt(LocalDateTime.now());

        Comment newComment = commentRepository.save(comment);

        postService.userCommentOnPost(postId);

        return mapper.map(newComment, CommentResponseDto.class);
    }

    @Override
    public boolean hasLike(long commentId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }
        return commentRepository.hasUserLikedComment(commentId, userId);
    }

    @Override
    public boolean hasDislike(long commentId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }
        return commentRepository.hasUserDislikedComment(commentId, userId);
    }

    @Override
    @Transactional
    public void dislikeComment(long commentId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }
        if(!commentRepository.hasUserDislikedComment(commentId, userId)){
            commentRepository.addDislike(commentId, userId);
            commentRepository.incrementDislikeCount(commentId);

            if(commentRepository.hasUserLikedComment(commentId, userId)){
                commentRepository.deleteLike(commentId, userId);
                commentRepository.decrementLikeCount(commentId);
            }
        }
    }

    @Override
    @Transactional
    public void likeComment(long commentId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }
        if(!commentRepository.hasUserLikedComment(commentId, userId)){
            commentRepository.addLike(commentId, userId);
            commentRepository.incrementLikeCount(commentId);

            if(commentRepository.hasUserDislikedComment(commentId, userId)){
                commentRepository.deleteDislike(commentId, userId);
                commentRepository.decrementDislikeCount(commentId);
            }
        }
    }

    @Override
    @Transactional
    public void noDislikeComment(long commentId, String currentUsername) {

        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(commentRepository.hasUserDislikedComment(commentId, userId)){
            commentRepository.deleteDislike(commentId, userId);
            commentRepository.decrementDislikeCount(commentId);
        }
    }

    @Override
    @Transactional
    public void noLikeComment(long commentId, String currentUsername) {
        Long userId = userRepository.findUserIdByUsername(currentUsername);
        if(userId == null){
            throw new ResourceNotFoundException("User", "username", currentUsername);
        }

        if(commentRepository.hasUserLikedComment(commentId, userId)){
            commentRepository.deleteLike(commentId, userId);
            commentRepository.decrementLikeCount(commentId);
        }
    }

    @Override
    public void userLikeComment(long commentId, String currentUsername) {
        CommentEventDto event = new CommentEventDto(commentId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.COMMENT_LIKE_QUEUE, event);
    }

    @Override
    public void userDislikeComment(long commentId, String currentUsername) {
        CommentEventDto event = new CommentEventDto(commentId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.COMMENT_DISLIKE_QUEUE, event);
    }

    @Override
    public void userNoLikeComment(long commentId, String currentUsername) {
        CommentEventDto event = new CommentEventDto(commentId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.COMMENT_NO_LIKE_QUEUE, event);
    }

    @Override
    public void userNoDislikeComment(long commentId, String currentUsername) {
        CommentEventDto event = new CommentEventDto(commentId, currentUsername);
        rabbitTemplate.convertAndSend(AppConstants.COMMENT_NO_DISLIKE_QUEUE, event);
    }

    @Override
    public List<CommentResponseDto> getCommentsByPostId(long postId) {
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new ResourceNotFoundException("Post", "id", Long.toString(postId)));

        List<Comment> comments = commentRepository.findByPostId(postId);

        List<CommentResponseDto> commentResponseDtos = comments.stream()
                .map(comment -> mapper.map(comment, CommentResponseDto.class))
                .collect(Collectors.toList());

        return CommentUtils.convertToCommentTree(commentResponseDtos);
    }

}
