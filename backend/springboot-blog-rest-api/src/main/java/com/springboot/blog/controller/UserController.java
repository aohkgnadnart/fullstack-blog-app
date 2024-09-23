package com.springboot.blog.controller;

import com.springboot.blog.payload.*;
import com.springboot.blog.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.springboot.blog.utils.AppConstants;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Build get top 5 users REST API
    @GetMapping("/top5")
    public ResponseEntity<List<UserVerySimpleResponseDto>> getTop5TagsByUserame(String username){
        return new ResponseEntity<>(userService.getTop5UsersByUsername(username), HttpStatus.OK);
    }

    // get user by id
    @GetMapping("/{id}")
    public ResponseEntity<UserFullResponseDto> getUserById(@PathVariable(name = "id") long id){
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @GetMapping("/me")
    public ResponseEntity<UserSimpleResponseDto> getCurrentUser(Authentication authentication) {
        String username = authentication.getName();
        UserSimpleResponseDto user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PutMapping("/change-password")
    public ResponseEntity<String> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto,
                                                 Authentication authentication) {
        userService.changePassword(authentication.getName(), passwordChangeDto);
        return new ResponseEntity<>("Password updated successfully", HttpStatus.OK);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/is-following/{userId}")
    public ResponseEntity<Boolean> isFollowing(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        boolean isFollowing = userService.isFollowing(currentUsername, userId);
        return ResponseEntity.ok(isFollowing);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/follow/{userId}")
    public ResponseEntity<String> followUser(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        userService.followUser(currentUsername, userId);
        return new ResponseEntity<>("You are now following user " + userId, HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/unfollow/{userId}")
    public ResponseEntity<String> unfollowUser(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        userService.unfollowUser(currentUsername, userId);
        return ResponseEntity.ok("You have unfollowed user " + userId);
    }


    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/is-friend/{userId}")
    public ResponseEntity<Boolean> isFriend(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        boolean isFriend = userService.isFriend(currentUsername, userId);
        return ResponseEntity.ok(isFriend);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/send-friend-request/{userId}")
    public ResponseEntity<String> sendFriendRequest(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        userService.sendFriendRequest(currentUsername, userId);
        return new ResponseEntity<>("Friend request sent to user " + userId, HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/accept-friend-request/{userId}")
    public ResponseEntity<String> acceptFriendRequest(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        userService.acceptFriendRequest(currentUsername, userId);
        return new ResponseEntity<>("Friend request accepted from user " + userId, HttpStatus.CREATED);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/unfriend/{userId}")
    public ResponseEntity<String> unfriendUser(@PathVariable Long userId, Authentication authentication) {
        String currentUsername = authentication.getName();
        userService.unfriendUser(currentUsername, userId);
        return ResponseEntity.ok("You have unfriended user " + userId);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_ADMIN)")
    @GetMapping("")
    public ResponseEntity<UserPageResponseDto> getAllUsers(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_USER_SORT_DIR, required = false) String sortDir){

        return new ResponseEntity<>(userService.getAllUsers(pageNo, pageSize, sortBy, sortDir), HttpStatus.OK);
    }


    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/followers")
    public ResponseEntity<UserPageResponseDto> getFollowerUsers(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_USER_SORT_DIR, required = false) String sortDir) {
        String currentUsername = authentication.getName();
        UserPageResponseDto followers = userService.getFollowerUsers(currentUsername, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(followers);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/following")
    public ResponseEntity<UserPageResponseDto> getFollowingUsers(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_USER_SORT_DIR, required = false) String sortDir) {
        String currentUsername = authentication.getName();
        UserPageResponseDto following = userService.getFollowingUsers(currentUsername, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(following);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/friends")
    public ResponseEntity<UserPageResponseDto> getFriends(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_USER_SORT_DIR, required = false) String sortDir) {
        String currentUsername = authentication.getName();
        UserPageResponseDto friends = userService.getFriends(currentUsername, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(friends);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/received-friend-requests")
    public ResponseEntity<UserPageResponseDto> getReceivedFriendRequests(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_USER_SORT_DIR, required = false) String sortDir) {
        String currentUsername = authentication.getName();
        UserPageResponseDto receivedRequests = userService.getReceivedFriendRequests(currentUsername, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(receivedRequests);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_USER)")
    @GetMapping("/sent-friend-requests")
    public ResponseEntity<UserPageResponseDto> getSentFriendRequests(
            Authentication authentication,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_USER_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_USER_SORT_DIR, required = false) String sortDir) {
        String currentUsername = authentication.getName();
        UserPageResponseDto sentRequests = userService.getSentFriendRequests(currentUsername, pageNo, pageSize, sortBy, sortDir);
        return ResponseEntity.ok(sentRequests);
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PostMapping("/{userId}/upgrade-to-admin")
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_ADMIN)")
    public ResponseEntity<String> upgradeToAdmin(@PathVariable long userId) {
        userService.upgradeToAdmin(userId);
        return ResponseEntity.ok("User upgraded to admin successfully");
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @DeleteMapping("/downgrade-from-admin")
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_ADMIN)")
    public ResponseEntity<?> downgradeFromAdmin(Authentication authentication) {
        String currentUsername = authentication.getName();
        userService.downgradeFromAdmin(currentUsername);
        return ResponseEntity.ok("User downgraded from admin successfully");
    }

    @SecurityRequirement(
            name = "Bear Authentication"
    )
    @PreAuthorize("hasRole(T(com.springboot.blog.utils.AppConstants).ROLE_ADMIN)")
    @GetMapping("/like-post/{postId}")
    public UserPageResponseDto getUsersWhoLikedPost(
            @PathVariable Long postId,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_USER_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_USER_PAGE_SIZE, required = false) int pageSize) {
        return userService.getUsersWhoLikedPost(postId, pageNo, pageSize);
    }
}
