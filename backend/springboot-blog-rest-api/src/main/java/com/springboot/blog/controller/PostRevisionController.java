package com.springboot.blog.controller;

import com.springboot.blog.payload.PostRevisionResponseDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/revisions")
@CrossOrigin(origins = "*")
@Tag(name = "REST APIs for Post Revision History")
public class PostRevisionController {

    private PostService postService;

    public PostRevisionController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Get Post Revision History REST API",
            description = "Get Post Revision History REST API is used to get all revisions of a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping
    public ResponseEntity<Page<PostRevisionResponseDto>> getPostRevisionHistory(
            @PathVariable(name = "postId") long postId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_POST_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_POST_PAGE_SIZE, required = false) int pageSize) {
        return ResponseEntity.ok(postService.getPostRevisionHistory(postId, pageNo, pageSize));
    }

    @Operation(
            summary = "Get Post Revision REST API",
            description = "Get Post Revision REST API is used to get a specific revision of a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/{revisionId}")
    public ResponseEntity<PostRevisionResponseDto> getPostRevision(
            @PathVariable(name = "postId") long postId,
            @PathVariable(name = "revisionId") long revisionId) {
        return ResponseEntity.ok(postService.getPostRevision(revisionId));
    }

    @Operation(
            summary = "Compare Post Revisions REST API",
            description = "Compare Post Revisions REST API is used to compare two revisions of a post"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http Status 200 SUCCESS"
    )
    @GetMapping("/compare")
    public ResponseEntity<String> compareRevisions(
            @PathVariable(name = "postId") long postId,
            @RequestParam(name = "revisionId1") long revisionId1,
            @RequestParam(name = "revisionId2") long revisionId2) {
        return ResponseEntity.ok(postService.compareRevisions(revisionId1, revisionId2));
    }
} 