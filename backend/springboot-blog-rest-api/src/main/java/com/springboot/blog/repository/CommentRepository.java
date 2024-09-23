package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query(value = "SELECT COUNT(*) FROM comment_likes WHERE comment_id = :commentId", nativeQuery = true)
    int countLikesByCommentId(@Param("commentId") long commentId);

    @Query(value = "SELECT COUNT(*) FROM comment_dislikes WHERE comment_id = :commentId", nativeQuery = true)
    int countDislikesByCommentId(@Param("commentId") long commentId);

    @Query(value = "SELECT COUNT(*) > 0 FROM comment_likes WHERE comment_id = :commentId AND user_id = :userId", nativeQuery = true)
    boolean hasUserLikedComment(@Param("commentId") long commentId, @Param("userId") long userId);

    @Query(value = "SELECT COUNT(*) > 0 FROM comment_dislikes WHERE comment_id = :commentId AND user_id = :userId", nativeQuery = true)
    boolean hasUserDislikedComment(@Param("commentId") long commentId, @Param("userId") long userId);

    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount + 1 WHERE c.id = :commentId")
    void incrementLikeCount(@Param("commentId") long commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount - 1 WHERE c.id = :commentId")
    void decrementLikeCount(@Param("commentId") long commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.dislikeCount = c.dislikeCount + 1 WHERE c.id = :commentId")
    void incrementDislikeCount(@Param("commentId") long commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.dislikeCount = c.dislikeCount - 1 WHERE c.id = :commentId")
    void decrementDislikeCount(@Param("commentId") long commentId);


    @Modifying
    @Query(value = "INSERT INTO comment_likes (comment_id, user_id) VALUES (:commentId, :userId)", nativeQuery = true)
    void addLike(@Param("commentId") long commentId, @Param("userId") long userId);

    @Modifying
    @Query(value = "INSERT INTO comment_dislikes (comment_id, user_id) VALUES (:commentId, :userId)", nativeQuery = true)
    void addDislike(@Param("commentId") long commentId, @Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM comment_likes WHERE comment_id = :commentId AND user_id = :userId", nativeQuery = true)
    void deleteLike(@Param("commentId") long commentId, @Param("userId") long userId);

    @Modifying
    @Query(value = "DELETE FROM comment_dislikes WHERE comment_id = :commentId AND user_id = :userId", nativeQuery = true)
    void deleteDislike(@Param("commentId") long commentId, @Param("userId") long userId);


    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    long countByPostId(@Param("postId") long postId);




    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
    List<Comment> findByPostId(long postId);



//    @Modifying
//    @Transactional
//    @Query("UPDATE Comment c SET c.replyCount = c.replyCount + 1 WHERE c.id = :parentCommentId")
//    void incrementReplyCount(Long parentCommentId);
//
//    @Modifying
//    @Transactional
//    @Query("UPDATE Comment c SET c.replyCount = c.replyCount - 1 WHERE c.id = :parentCommentId")
//    void decrementReplyCount(Long parentCommentId);
}
