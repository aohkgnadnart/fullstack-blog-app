package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.Tag;
import com.springboot.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

//    @Query("SELECT u.followingUsers FROM User u WHERE u.id = :userId")
//    Page<User> findFollowingUsersByUserId(@Param("userId") Long userId, Pageable pageable);
//
//    @Query("SELECT u.followerUsers FROM User u WHERE u.id = :userId")
//    Page<User> findFollowerUsersByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query(value = "SELECT * FROM users WHERE username LIKE %:username% LIMIT 5", nativeQuery = true)
    List<User> findTop5ByUsernameContains(@Param("username") String username);



    @Query("SELECT u FROM User u JOIN u.followerUsers f WHERE f.username = :username")
    Page<User> findFollowingUsers(String username, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.followingUsers f WHERE f.username = :username")
    Page<User> findFollowerUsers(String username, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.friends f WHERE f.username = :username")
    Page<User> findFriends(String username, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.friendRequestsSent f WHERE f.username = :username")
    Page<User> findReceivedFriendRequests(String username, Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.friendRequestsReceived f WHERE f.username = :username")
    Page<User> findSentFriendRequests(String username, Pageable pageable);


    @Query("SELECT u.id FROM User u WHERE u.username = :username")
    Long findUserIdByUsername(@Param("username") String username);


    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_roles (user_id, role_id) " +
            "SELECT :userId, r.id " +
            "FROM roles r " +
            "WHERE r.name = 'ROLE_ADMIN' " +
            "AND NOT EXISTS (SELECT 1 FROM users_roles ur WHERE ur.user_id = :userId AND ur.role_id = r.id)", nativeQuery = true)
    void upgradeToAdmin(@Param("userId") long userId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM users_roles " +
            "WHERE user_id = :userId AND role_id = (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')", nativeQuery = true)
    void downgradeFromAdmin(@Param("userId") long userId);
}
