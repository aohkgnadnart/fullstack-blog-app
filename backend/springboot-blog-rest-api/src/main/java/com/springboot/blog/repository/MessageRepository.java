package com.springboot.blog.repository;

import com.springboot.blog.entity.Comment;
import com.springboot.blog.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface MessageRepository  extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :curUserId AND m.recipient.id = :userId) OR (m.recipient.id = :curUserId AND m.sender.id = :userId) ORDER BY m.id DESC")
    Page<Message> findMessagesBetweenUsers(@Param("userId") long userId, @Param("curUserId") long curUserId, Pageable pageable);


}
