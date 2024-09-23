package com.springboot.blog.controller;

import com.springboot.blog.payload.CommentCreateDto;
import com.springboot.blog.payload.CommentResponseDto;
import com.springboot.blog.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/posts/{postId}/comments/{parentCommentId}")
    public ResponseEntity<CommentResponseDto> createReply(@PathVariable(value = "postId") long postId,
                                                          @PathVariable(value = "parentCommentId") long parentCommentId,
                                                          @Valid @RequestBody CommentCreateDto commentCreateDto,
                                                          Authentication authentication
                                                          ){
        String currentUsername = authentication.getName();
        return new ResponseEntity<>(commentService.createReply(postId, parentCommentId, commentCreateDto, currentUsername), HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable(value = "postId") long postId,
                                                            @Valid @RequestBody CommentCreateDto commentCreateDto,
                                                            Authentication authentication){
        String currentUsername = authentication.getName();
        return new ResponseEntity<>(commentService.createComment(postId, commentCreateDto, currentUsername), HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable(name = "id") long id){
        commentService.deleteCommentById(id);
        return new ResponseEntity("Comment entity deleted successfully", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/comments/{id}/like")
    public ResponseEntity<String> userLikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        commentService.userLikeComment(id, currentUsername);
        return new ResponseEntity<>("like!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/comments/{id}/no-like")
    public ResponseEntity<String> userNoLikeComment(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        commentService.userNoLikeComment(id, currentUsername);
        return new ResponseEntity<>("no-like!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/comments/{id}/dislike")
    public ResponseEntity<String> userDislikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        commentService.userDislikeComment(id, currentUsername);
        return new ResponseEntity<>("dislike!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/comments/{id}/no-dislike")
    public ResponseEntity<String> userNoDislikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        commentService.userNoDislikeComment(id, currentUsername);
        return new ResponseEntity<>("no-dislike!", HttpStatus.OK);
    }


    @GetMapping("/posts/{postID}/comments")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable(name = "postID") Long postId){
        return new ResponseEntity<>(commentService.getCommentsByPostId(postId), HttpStatus.OK);
    }

//    @SecurityRequirement(
//            name = "Bear Authentication"
//    )
//    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
//    @GetMapping("/posts/{postID}/commentsPersonalized")
//    public ResponseEntity<List<CommentPersonalizedResponseDto>> getCommentsPersonalizedByPostId(@PathVariable(name = "postID") Long postId,
//                                                                                                Authentication authentication){
//        String currentUsername = authentication.getName();
//        return new ResponseEntity<>(commentService.getCommentsPersonalizedByPostId(postId, currentUsername), HttpStatus.OK);
//    }


}
