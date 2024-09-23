package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import com.springboot.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContaining(String title, Pageable pageable);

    boolean existsByTitle(String title);

    @Query("SELECT p FROM Post p JOIN p.tags t WHERE t.id = :tagId")
    Page<Post> findByTagId(@Param("tagId") Long tagId, Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    Page<Post> findByUserId(@Param("userId") Long userId, Pageable pageable);

    Page<Post> findByUserIn(List<User> users, Pageable pageable);



    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Post p JOIN p.viewers v WHERE p.id = :postId AND v.id = :userId")
    boolean hasUserViewedPost(@Param("postId") long postId, @Param("userId") long userId);

    @Query("SELECT CASE WHEN COUNT(l) > 0 THEN true ELSE false END FROM Post p JOIN p.likes l WHERE p.id = :postId AND l.id = :userId")
    boolean hasUserLikedPost(@Param("postId") long postId, @Param("userId") long userId);

    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Post p JOIN p.dislikes d WHERE p.id = :postId AND d.id = :userId")
    boolean hasUserDislikedPost(@Param("postId") long postId, @Param("userId") long userId);


//    @Query("SELECT COUNT(v) FROM Post p JOIN p.viewers v WHERE p.id = :postId")
//    int countViewersByPostId(@Param("postId") long postId);
//
//    @Query("SELECT COUNT(l) FROM Post p JOIN p.likes l WHERE p.id = :postId")
//    int countLikesByPostId(@Param("postId") long postId);
//
//    @Query("SELECT COUNT(d) FROM Post p JOIN p.dislikes d WHERE p.id = :postId")
//    int countDislikesByPostId(@Param("postId") long postId);


    @Modifying
    @Query(value = "DELETE FROM posts_likes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void deleteLike(@Param("postId") long postId, @Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM posts_dislikes WHERE post_id = :postId AND user_id = :userId", nativeQuery = true)
    void deleteDislike(@Param("postId") long postId, @Param("userId") long userId);



    @Modifying
    @Query(value = "INSERT INTO posts_likes (post_id, user_id) VALUES (:postId, :userId)", nativeQuery = true)
    void addLike(@Param("postId") long postId, @Param("userId") long userId);

    @Modifying
    @Query(value = "INSERT INTO posts_dislikes (post_id, user_id) VALUES (:postId, :userId)", nativeQuery = true)
    void addDislike(@Param("postId") long postId, @Param("userId") long userId);

    @Modifying
    @Query(value = "INSERT INTO posts_viewers (post_id, user_id) VALUES (:postId, :userId)", nativeQuery = true)
    void addViewer(@Param("postId") long postId, @Param("userId") long userId);


    @Modifying
    @Query("UPDATE Post p SET p.dislikeCount = p.dislikeCount + 1 WHERE p.id = :postId")
    void incrementDislikeCount(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.dislikeCount = p.dislikeCount - 1 WHERE p.id = :postId")
    void decrementDislikeCount(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :postId")
    void incrementLikeCount(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :postId")
    void decrementLikeCount(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.viewerCount = p.viewerCount + 1 WHERE p.id = :postId")
    void incrementViewerCount(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.commentCount = p.commentCount + 1 WHERE p.id = :postId")
    void incrementCommentCount(@Param("postId") long postId);

    @Modifying
    @Query("UPDATE Post p SET p.commentCount = :currentCommentCount WHERE p.id = :postId")
    void decrementCommentCount(@Param("postId") long postId, @Param("currentCommentCount") long currentCommentCount);


    @Query("SELECT u FROM Post p JOIN p.likes u WHERE p.id = :postId")
    Page<User> findUsersWhoLikedPost(@Param("postId") long postId, Pageable pageable);

}
