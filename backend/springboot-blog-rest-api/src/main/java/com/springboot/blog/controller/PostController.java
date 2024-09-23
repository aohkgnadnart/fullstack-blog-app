package com.springboot.blog.controller;

import com.springboot.blog.payload.PostFullResponseDto;
import com.springboot.blog.payload.PostPageResponseDto;
import com.springboot.blog.payload.PostRequestDto;
import com.springboot.blog.service.PostService;
import com.springboot.blog.utils.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(
        name = "CRUD REST APIs for post resource"
)
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Create Post REST API",
            description = "Create Post REST API is used to save post into database"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Http status 201 CREATED"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    // create blog post
//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostFullResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto){
        return new ResponseEntity<>(postService.createPost(postRequestDto), HttpStatus.CREATED);
    }

    @Operation(
            summary = "get all posts REST API",
            description = "Get all posts REST API is used to fetch all the posts from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    // get all posts
    @GetMapping("")
    public ResponseEntity<PostPageResponseDto> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_POST_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_POST_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_POST_SORT_DIR, required = false) String sortDir){

        return new ResponseEntity<>(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<PostPageResponseDto> getPostsByTitle(
            String title,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_POST_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_POST_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_POST_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getPostsByTitle(title, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/tags/{id}")
    public ResponseEntity<PostPageResponseDto> getPostsByTagId(
            @PathVariable(value = "id") long tagId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_POST_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_POST_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_POST_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getPostsByTagId(tagId, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<PostPageResponseDto> getPostsByUserId(
            @PathVariable(value = "id") long userId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_POST_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_POST_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_POST_SORT_DIR, required = false) String sortDir
    ){
        return new ResponseEntity<>(postService.getPostsByUserId(userId, pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Post by Id REST API",
            description = "Get Post by Id REST API is used to get single post from the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    // get post by id
    @GetMapping("/{id}")
    public ResponseEntity<PostFullResponseDto> getPostById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(postService.getPostById(id));
    }

//    @SecurityRequirement(
//            name = "Bear Authentication"
//    )
//    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
//    @GetMapping("/{id}/personalized")
//    public ResponseEntity<PostPersonalizedResponseDto> getPostByIdd(@PathVariable(name = "id") long id){
//        return ResponseEntity.ok(postService.getPostPersonalizedById(id));
//    }

    @Operation(
            summary = "update post REST API",
            description = "Update post REST API is used to update a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    // update post by id rest api
//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostFullResponseDto> updatePost(@PathVariable(name = "id") long id, @Valid @RequestBody PostRequestDto postRequestDto){
        return ResponseEntity.ok(postService.updatePost(postRequestDto, id));
    }

    @Operation(
            summary = "delete post REST API",
            description = "Delete post REST API is used to delete a particular post in the database"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Http status 200 SUCCESS"
    )
    @SecurityRequirement(
            name = "Bear Authentication"
    )
    // delete post rest api
//    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id){
        postService.deletePostById(id);
        return new ResponseEntity("Post entity deleted successfully", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/following")
    public ResponseEntity<PostPageResponseDto> getPostsFromFollowingUsers(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_POST_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_POST_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_POST_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_POST_SORT_DIR, required = false) String sortDir){

        String currentUsername = authentication.getName();
        PostPageResponseDto posts = postService.getPostsFromFollowingUsers(currentUsername, pageNo, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/{id}/viewers")
    public ResponseEntity<String> userViewedPost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        postService.userViewPost(id, currentUsername);
        return new ResponseEntity<>("seen!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/{postId}/is-like")
    public ResponseEntity<Boolean> isLike(@PathVariable Long postId, Authentication authentication) {
        String currentUsername = authentication.getName();
        boolean isLike = postService.hasLike(postId, currentUsername);
        return ResponseEntity.ok(isLike);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/{postId}/is-dislike")
    public ResponseEntity<Boolean> isDislike(@PathVariable Long postId, Authentication authentication) {
        String currentUsername = authentication.getName();
        boolean isLike = postService.hasDislike(postId, currentUsername);
        return ResponseEntity.ok(isLike);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/{id}/like")
    public ResponseEntity<String> userLikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        postService.userLikePost(id, currentUsername);
        return new ResponseEntity<>("like!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/{id}/no-like")
    public ResponseEntity<String> userNoLikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        postService.userNoLikePost(id, currentUsername);
        return new ResponseEntity<>("no-like!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/{id}/dislike")
    public ResponseEntity<String> userDislikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        postService.userDislikePost(id, currentUsername);
        return new ResponseEntity<>("dislike!", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/{id}/no-dislike")
    public ResponseEntity<String> userNoDislikePost(Authentication authentication, @PathVariable(name = "id") long id) {
        String currentUsername = authentication.getName();
        postService.userNoDislikePost(id, currentUsername);
        return new ResponseEntity<>("no-dislike!", HttpStatus.OK);
    }
}
