package com.springboot.blog.service;

import com.springboot.blog.payload.*;

import java.util.List;

public interface UserService {

//    UserSimpleResponseDto getByUsernameOrEmail(String usernameOrEmail);

    UserFullResponseDto getUserById(long id);

//    UserPageResponseDto getFollowingUsersById(long userId, int pageNo, int pageSize, String sortBy, String sortDir);
//
//    UserPageResponseDto getFollowerUsersById(long userId, int pageNo, int pageSize, String sortBy, String sortDir);

    List<UserVerySimpleResponseDto> getTop5UsersByUsername(String username);

    UserSimpleResponseDto getUserByUsername(String username);

    void changePassword(String username, PasswordChangeDto passwordChangeDto);


    boolean isFollowing(String currentUsername, Long userId);

    void followUser(String currentUsername, Long userId);

    void unfollowUser(String currentUsername, Long userId);

    boolean isFriend(String currentUsername, Long userId);

    void sendFriendRequest(String currentUsername, Long userId);

    void acceptFriendRequest(String currentUsername, Long userId);

    void unfriendUser(String currentUsername, Long userId);


    UserPageResponseDto getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);

    UserPageResponseDto getFollowerUsers(String username, int pageNo, int pageSize, String sortBy, String sortDir);

    UserPageResponseDto getFollowingUsers(String username, int pageNo, int pageSize, String sortBy, String sortDir);

    UserPageResponseDto getFriends(String username, int pageNo, int pageSize, String sortBy, String sortDir);

    UserPageResponseDto getReceivedFriendRequests(String username, int pageNo, int pageSize, String sortBy, String sortDir);

    UserPageResponseDto getSentFriendRequests(String username, int pageNo, int pageSize, String sortBy, String sortDir);


    void upgradeToAdmin(long userId);

    void downgradeFromAdmin(String currentUsername);

    UserPageResponseDto getUsersWhoLikedPost(long postId, int pageNo, int pageSize);
}
